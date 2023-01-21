package com.codework.utility;

import com.codework.enums.SolutionResult;
import com.codework.exception.BusinessException;
import com.codework.model.BulkUploadSubmission;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BulkUploadSolutionUtility {

    public static List<BulkUploadSubmission> getBulkSolutionList(MultipartFile file) throws BusinessException, ParseException {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            List<BulkUploadSubmission> bulkUploadSubmissions = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                BulkUploadSubmission submission = new BulkUploadSubmission();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            submission.setUsername(currentCell.getStringCellValue());
                            break;
                        case 1:
                            if(currentCell.getNumericCellValue()!=0){
                                submission.setTimeTaken((long)currentCell.getNumericCellValue());
                            }
                            break;
                        case 2:
                            submission.setStartTime(simpleDateFormat.parse(currentCell.getStringCellValue()));
                            break;
                        case 3:
                            submission.setSubmissionTime(simpleDateFormat.parse(currentCell.getStringCellValue()));
                            break;
                        case 4:
                            submission.setSolution(currentCell.getStringCellValue());
                            break;
                        case 5:
                            submission.setSolutionResult(SolutionResult.getValue(currentCell.toString()));
                            break;
                        case 6:
                            submission.setPoints(Float.parseFloat(currentCell.toString()));
                            break;
                        case 7:
                            submission.setRemarks(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                bulkUploadSubmissions.add(submission);
            }
            workbook.close();
            return bulkUploadSubmissions;
        }catch(IOException exception){
            throw new BusinessException("Failed to parse excel file");
        }
    }

}
