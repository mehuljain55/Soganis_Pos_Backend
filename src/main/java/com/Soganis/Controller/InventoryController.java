package com.Soganis.Controller;

import com.Soganis.Entity.PurchaseOrderBook;
import com.Soganis.Model.ItemAddModel;
import com.Soganis.Service.InventoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
public class InventoryController {
    
      @Autowired
    private InventoryService inventoryService;
    
      @PostMapping("/generate_order")
    public ResponseEntity<InputStreamResource> generateOrder(@RequestBody List<PurchaseOrderBook> orders) {
        try {
            String status = inventoryService.updateOrder(orders);
    Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Orders");

            // Create a bold font for header
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);

            // Create a cell style for headers
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);

            // Create the header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Sno", "Description", "Color", "Size", "Quantity", "Item Type", "School"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle); // Apply bold style to header
            }

            // Fill data
            int rowNum = 1;
            int count = 1;
            for (PurchaseOrderBook order : orders) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(count);
                row.createCell(1).setCellValue(order.getDescription());
                row.createCell(2).setCellValue(order.getColor());
                row.createCell(3).setCellValue(order.getSize().toString());
                row.createCell(4).setCellValue(order.getQuantity());
                row.createCell(5).setCellValue(order.getItemType());
                row.createCell(6).setCellValue(order.getSchool());
                count++;
            }

            // Adjust column widths
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the workbook to a ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();

            // Convert ByteArrayOutputStream to InputStreamResource
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            InputStreamResource resource = new InputStreamResource(bis);

            // Prepare the response with the Excel file
            String filename = "order" + new Random().nextInt(100) + ".xlsx";
            HttpHeaders headers1 = new HttpHeaders();
            headers1.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            headers1.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers1.setContentLength(baos.size());

            return new ResponseEntity<>(resource, headers1, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/update_inventory")
    public String updateInventory(@RequestBody List<ItemAddModel> itemAddModel )
    {
    String status=inventoryService.addItemsInventory(itemAddModel);
    return status;
        
    }
    
    
}
