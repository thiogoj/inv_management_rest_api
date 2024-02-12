package joaquinthiogo.inventorymanagementapi.service;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Item;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import joaquinthiogo.inventorymanagementapi.model.item.csv.CsvResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileCSVService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UnitRepository unitRepository;

    public CsvResponse insert(User user, MultipartFile file) throws IOException {
        if (file.getOriginalFilename().endsWith(".csv")) {
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            String fileName = FilenameUtils.removeExtension(file.getOriginalFilename()) + "_" + format + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            Path path = Paths.get("csv-file/" + fileName);
            file.transferTo(path);

            try (Reader reader = Files.newBufferedReader(path);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());) {
                List<String> lines = Files.readAllLines(path);
                List<String> updatedLines = new ArrayList<>();

                for (String line : lines) {
                    String updatedLine = line.replace(';', ',');
                    updatedLines.add(updatedLine);
                }

                Files.write(path, updatedLines, StandardOpenOption.TRUNCATE_EXISTING);
            }

            return CsvResponse.builder()
                    .fileName(fileName)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only CSV files are allowed");
        }
    }


    public List<ItemResponse> readCSVtoItem(User user, String csvFilename) throws IOException {
        Path path = Paths.get("csv-file/" + csvFilename);

        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader().build();

                CSVParser parse = new CSVParser(reader, csvFormat);
                List<ItemResponse> itemResponses = new ArrayList<>();

                parse.forEach(value -> {
                    Item item = new Item();
                    item.setNoPart(value.get(0));
                    item.setDescription(value.get(1));
                    item.setHsCode(Integer.parseInt(value.get(2)));
                    item.setItemType(value.get(3));

                    Unit unit = new Unit();
                    unit.setName(value.get(4));
                    item.setUnit(unit);

                    itemResponses.add(ItemResponse.builder()
                            .id(null)
                            .noPart(item.getNoPart())
                            .description(item.getDescription())
                            .hsCode(item.getHsCode())
                            .itemType(item.getItemType())
                            .unit(UnitResponse.builder()
                                    .id(null)
                                    .name(item.getUnit().getName())
                                    .build())
                            .build());
                });
                return itemResponses;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is problem");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CSV file is not found");
        }
    }

    public List<CsvResponse> findAll(User user) {
        URI uri = Paths.get("csv-file").toUri();
        File file = new File(uri);

        List<CsvResponse> csvResponseList = new ArrayList<>();
        File[] listFiles = file.listFiles();

        if (listFiles != null) {
            for (File listFile : listFiles) {
                if (listFile.getName().endsWith(".csv")) {
                    CsvResponse csvResponse = new CsvResponse(listFile.getName());
                    csvResponseList.add(csvResponse);
                }
            }
        }
        return csvResponseList;
    }

    public void delete(User user, String fileName) {
        URI uri = Paths.get("csv-file/" + fileName).toUri();
        File file = new File(uri);

        if (file.exists()) {
            file.delete();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CSV file is not found");
        }
    }

    @Transactional
    public List<ItemResponse> insertToItems(User user, String csvFilename) {
        Path path = Paths.get("csv-file/" + csvFilename);

        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                CSVFormat csvFormat = CSVFormat.EXCEL.builder().setHeader().build();

                CSVParser parse = new CSVParser(reader, csvFormat);
                List<ItemResponse> itemResponses = new ArrayList<>();

                parse.forEach(value -> {
                    Item item = new Item();
                    item.setId(UUID.randomUUID().toString());
                    item.setNoPart(value.get(0));
                    item.setDescription(value.get(1));
                    item.setHsCode(Integer.parseInt(value.get(2)));
                    item.setItemType(value.get(3));

                    Unit unit = unitRepository.findFirstByName(value.get(4)).orElseThrow(() -> new RuntimeException("Unit is not found"));
                    item.setUnit(unit);

                    Item saveItem = itemRepository.save(item);

                    itemResponses.add(ItemResponse.builder()
                            .id(saveItem.getId())
                            .noPart(saveItem.getNoPart())
                            .description(saveItem.getDescription())
                            .hsCode(saveItem.getHsCode())
                            .itemType(saveItem.getItemType())
                            .unit(UnitResponse.builder()
                                    .id(saveItem.getUnit().getId())
                                    .name(saveItem.getUnit().getName())
                                    .build())
                            .build());
                });
                return itemResponses;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CSV file is not found");
        }
    }

}
