package com.Soganis.Service;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.PurchaseOrderBook;
import com.Soganis.Model.ItemAddModel;
import com.Soganis.Model.ItemAddStockModel;
import com.Soganis.Repository.ItemListRepository;
import com.Soganis.Repository.ItemsRepository;
import com.Soganis.Repository.PurchaseOrderBookRepo;
import com.Soganis.Repository.SchoolRepository;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private ItemsRepository itemRepo;

    @Autowired
    private PurchaseOrderBookRepo purchaseOrderRepo;
    
    @Autowired
    private SchoolRepository schoolRepo;
    
    @Autowired
    private ItemListRepository itemListRepo;

    public String updateInventory(BillingModel billing) {

        try {

            Items item = itemRepo.getItemByItemBarcodeID(billing.getItemBarcodeID());

            if (item.getGroup_id().equals("NA")) {
                int item_sold = billing.getQuantity();
                int updatedInventoryQuantity = item.getQuantity() - item_sold;

                if (updatedInventoryQuantity <= 0) {
                    updatedInventoryQuantity = 0;
                }
                item.setQuantity(updatedInventoryQuantity);
                itemRepo.save(item);
                return "Success";
            } else {

                List<Items> groupList = itemRepo.findItemsByGroupId(item.getGroup_id());
                for (Items sale : groupList) {
                    int item_sold = billing.getQuantity();
                    int updatedInventoryQuantity = item.getQuantity() - item_sold;

                    if (updatedInventoryQuantity <= 0) {
                        updatedInventoryQuantity = 0;
                    }
                    sale.setQuantity(updatedInventoryQuantity);
                    itemRepo.save(sale);

                }
                return "Success";

            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public String generate_order(String barcodedId) {
        try {
            Items item = itemRepo.getItemByItemBarcodeID(barcodedId);
            PurchaseOrderBook order = new PurchaseOrderBook();
            String description = item.getItemCategory() + " " + item.getItemType();
            int currentStock = item.getQuantity();
            order.setDescription(description);
            order.setBarcodedId(barcodedId);
            order.setItemType(item.getItemType());
            order.setSchool(item.getItemCategory());
            order.setDate(new Date());
            order.setSize(item.getItemSize());
            order.setColor(item.getItemColor());
            order.setStatus("NOT GENERATED");
            order.setCurrentStock(currentStock);
            purchaseOrderRepo.save(order);
            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
    
   
    
    public List<PurchaseOrderBook>  view_order()
    {
      List<PurchaseOrderBook> lst=purchaseOrderRepo.findItemsWithStatusNotGenerated();
      return lst;
    }
    
    public String deletePurchaseOrder(int orderId)
    {
      try{
          Optional<PurchaseOrderBook> opt=purchaseOrderRepo.findById(orderId);
          if(opt.isPresent())
          {
          PurchaseOrderBook order=opt.get();
          purchaseOrderRepo.deleteById(orderId);
         
          }
           return "Success";
      
      }catch(Exception e)
      {
       e.printStackTrace();
       return "Failed";
      }
    }
    
    public String updateOrder(List<PurchaseOrderBook> orders)
    {
      for(PurchaseOrderBook order:orders)
      {
        order.setStatus("GENERATED");
        purchaseOrderRepo.save(order);
      }
      return "Success";
    }
    
    public String addItemsInventory(List<ItemAddModel> itemList)
    {
      for(ItemAddModel itemAddModel:itemList)
      {
       Items item=itemRepo.getItemByItemBarcodeID(itemAddModel.getBarcodedId());
       int total_quantity=item.getQuantity()+itemAddModel.getQuantity();
       item.setQuantity(total_quantity);
       itemRepo.save(item);
      }
      return "Success";
    }
    
    public String addItemStock(List<ItemAddStockModel> itemList)
    {
        try{
         for(ItemAddStockModel itemModel:itemList)
         {
            Items itemAddModel=new Items();
            Items item=itemRepo.save(itemAddModel);
            String barcodeId=generateBarcode(item.getSno());
            item.setItemBarcodeID(barcodeId);
            item.setItemCode(itemModel.getItemCode());
            item.setItemName(itemModel.getItemName());
            item.setItemCategory(itemModel.getItemCategory());
            item.setItemColor(itemModel.getItemColor());
            item.setItemSize(itemModel.getItemSize());
            item.setItemType(itemModel.getItemType());
            item.setPrice(itemModel.getPrice());
            item.setWholeSalePrice(itemModel.getWholeSalePrice());
            String schoolCode=schoolRepo.findSchoolCodeBySchoolName(item.getItemCategory());
            String itemTypeCode=itemListRepo.findItemTypeCodeByDescription(item.getItemType());
            item.setQuantity(itemModel.getQuantity());
            item.setDescription(itemModel.getDescription());
            item.setSchoolCode(schoolCode);
            item.setItemTypeCode(itemTypeCode);
            if(itemModel.getGroupId()==null)
            {
                item.setGroup_id("NA");
            }
            else if(itemModel.getGroupId().equals(""))
            {
                System.out.println("Group Id");
             item.setGroup_id("NA");
            }
            else{
                String groupId=itemModel.getGroupId()+item.getItemSize();
                item.setGroup_id(groupId);
             }
            itemRepo.save(item);  
         }
       
   
         return "Success";
        }catch(Exception e)
        {
         e.printStackTrace();
         return "Failed";
        }
        }
   
    
    public List<String> school_list()
    {
        List<String> school_list=schoolRepo.findSchoolList();
        return school_list;
    }
    
       public List<String> item_list()
    {
        List<String> item_list=itemListRepo.findItemType();
        return item_list;
    }
    
      public String generateBarcode(int id) {

        String prefix = "SG";
        String idStr = String.format("%09d", id);
        String barcodeId = prefix + idStr;

        return barcodeId;
    }
      
      public String checkItemCode(String itemCode)
      {
        List<Items> items=itemRepo.checkItemCodeForNewItem(itemCode);
        
        if(items==null)
        {
          return "New";
        }else if(items.size()==0)
        { 
            return "New";
        }else{
          return "Exists";
        }
        
        
        
      }

  
    
    
//   public String generateInventoryExcel() throws IOException {
//    // Create a new workbook and sheet
//    
//    
//    List<String> itemList=itemRepo.findDistinctItemTypes();
//    
//    for(String item:itemList)
//    {
//    
//    Workbook workbook = new XSSFWorkbook();
//    Sheet sheet = workbook.createSheet(item);
//
//    String item_code=itemRepo.findDistinctItemTypeCode(item);
//    
//
//    
//    
//    // Set headings
//    Row row1 = sheet.createRow(0); // Row 1
//    Cell itemCodeHeader = row1.createCell(0); // A1
//    itemCodeHeader.setCellValue("Item Code");
//
//    Row row2 = sheet.createRow(1); // Row 2
//    Cell schoolCodeHeader = row2.createCell(0); // A2
//    schoolCodeHeader.setCellValue("School Code");
//
//    // Fetch distinct item sizes and school codes for "Full Pant"
//    List<String> itemSizes = itemRepo.findDistinctItemSizeByItemType(item);
//    List<String> schoolCodes = itemRepo.findDistinctSchoolCodeByItemType(item);
//
//    // Populate item sizes in column A starting from A3
//    int rowIndex = 2; // Start filling sizes from row 3 (A3)
//    for (String size : itemSizes) {
//        Row sizeRow = sheet.getRow(rowIndex);
//        if (sizeRow == null) {
//            sizeRow = sheet.createRow(rowIndex);
//        }
//        Cell sizeCell = sizeRow.createCell(0); // Column A
//        sizeCell.setCellValue(size);
//        rowIndex++;
//    }
//
//    // Populate school codes in row 2 starting from B2
//    int currentColIndex = 1; // Start filling school codes from column B
//    for (String school : schoolCodes) {
//        Cell schoolCodeCell = row2.createCell(currentColIndex); // Row 2
//        schoolCodeCell.setCellValue(school);
//        currentColIndex++;
//    }
//
//    // Populate "FP" in row 1 (starting from A2 to the last school code column)
//    for (int i = 1; i < currentColIndex; i++) { // Starting from column B (index 1)
//        Cell fpCell = row1.createCell(i); // Row 1
//        fpCell.setCellValue(item_code);
//    }
//
//    // Resize columns to fit the content
//    for (int i = 0; i < sheet.getRow(1).getLastCellNum(); i++) {
//        sheet.autoSizeColumn(i);
//    }
//
//    // Write the workbook to file
//    try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\mehul\\Desktop\\Invoice\\"+item+".xlsx")) {
//        workbook.write(fileOut);
//    }
//
//    // Closing the workbook
//    workbook.close();
//    }
//    return "Success";
//}
    
    
    public String generateInventoryExcel() throws IOException {
    // Create a new workbook
    Workbook workbook = new XSSFWorkbook();

    // Fetch distinct item types
    List<String> itemList = itemRepo.findDistinctItemTypes();

    // Loop through each item type to create individual sheets
    for (String item : itemList) {
        // Create a new sheet for each item
        Sheet sheet = workbook.createSheet(item);

        // Fetch the item code for the item type
        String item_code = itemRepo.findDistinctItemTypeCode(item);

        // Set headings
        Row row1 = sheet.createRow(0); // Row 1
        Cell itemCodeHeader = row1.createCell(0); // A1
        itemCodeHeader.setCellValue("Item Code");

        Row row2 = sheet.createRow(1); // Row 2
        Cell schoolCodeHeader = row2.createCell(0); // A2
        schoolCodeHeader.setCellValue("School Code");

        // Fetch distinct item sizes and school codes for the item type
        List<String> itemSizes = getSortedItemSizes(itemRepo.findDistinctItemSizeByItemType(item));
        
        
        List<String> schoolCodes = itemRepo.findDistinctSchoolCodeByItemType(item);

        // Populate item sizes in column A starting from A3
        int rowIndex = 2; // Start filling sizes from row 3 (A3)
        for (String size : itemSizes) {
            Row sizeRow = sheet.getRow(rowIndex);
            if (sizeRow == null) {
                sizeRow = sheet.createRow(rowIndex);
            }
            Cell sizeCell = sizeRow.createCell(0); // Column A
            sizeCell.setCellValue(size);
            rowIndex++;
        }

        // Populate school codes in row 2 starting from B2
        int currentColIndex = 1; // Start filling school codes from column B
        for (String school : schoolCodes) {
            Cell schoolCodeCell = row2.createCell(currentColIndex); // Row 2
            schoolCodeCell.setCellValue(school);
            currentColIndex++;
        }

        // Populate the item code in row 1 (starting from A2 to the last school code column)
        for (int i = 1; i < currentColIndex; i++) { // Starting from column B (index 1)
            Cell fpCell = row1.createCell(i); // Row 1
            fpCell.setCellValue(item_code);
        }

        // Resize columns to fit the content
        for (int i = 0; i < sheet.getRow(1).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    // Write the workbook to a file
    try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\mehul\\Desktop\\Invoice\\Inventory.xlsx")) {
        workbook.write(fileOut);
    }

    // Close the workbook
    workbook.close();

    return "Success";
}
    
    public List<String> getSortedItemSizes(List<String> itemSizes) {
    return itemSizes.stream()
        .sorted(Comparator.comparing((String s) -> isNumeric(s) ? 0 : 1)
                .thenComparing(s -> isNumeric(s) ? Integer.parseInt(s) : 0)
                .thenComparing(s -> s))
        .collect(Collectors.toList());
}

// Helper method to check if a string is numeric
private boolean isNumeric(String str) {
    return str != null && str.matches("\\d+");
}




    
}
    
    
    


