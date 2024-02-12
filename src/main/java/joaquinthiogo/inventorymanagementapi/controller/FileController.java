package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import joaquinthiogo.inventorymanagementapi.model.item.csv.CsvResponse;
import joaquinthiogo.inventorymanagementapi.service.FileCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileCSVService fileCSVService;

    @PostMapping(path = "/api/upload/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebResponse<CsvResponse> insert(User user, @RequestPart(name = "csv") MultipartFile file) throws IOException {
        CsvResponse response = fileCSVService.insert(user, file);
        return WebResponse.<CsvResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(path = "/api/csv/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ItemResponse>> readCsv(User user, @PathVariable(value = "fileName") String fileName) throws IOException {
        List<ItemResponse> itemResponses = fileCSVService.readCSVtoItem(user, fileName);
        return WebResponse.<List<ItemResponse>>builder()
                .data(itemResponses)
                .build();
    }

    @GetMapping(path = "/api/csv", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CsvResponse>> findAll(User user) {
        List<CsvResponse> responseList = fileCSVService.findAll(user);
        return WebResponse.<List<CsvResponse>>builder()
                .data(responseList)
                .build();
    }

    @PostMapping(path = "/api/csv/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ItemResponse>> insertToItem(User user, @PathVariable(name = "fileName") String fileName) {
        List<ItemResponse> itemResponses = fileCSVService.insertToItems(user, fileName);
        return WebResponse.<List<ItemResponse>>builder()
                .data(itemResponses)
                .build();
    }

    @DeleteMapping(path = "/api/csv/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable(name = "fileName") String fileName) {
        fileCSVService.delete(user, fileName);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }
}
