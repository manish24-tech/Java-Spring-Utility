import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * To provide utilities for multiple module to achieve re-usability 
 * This utility class contain excel-sheet builder methods
 * 
 * @author manish.luste
 */
public interface ExcellPoiUtil {
	// static final Log log = LogFactoryUtil.getLog(ExcellPoiUtil.class);
	static final String DATE_FORMATE = "dd-MM-yyyy";

	/**
	 * Responsible to set cell style align to center. ex. # column value should be in center
	 * @param workbooks construct whether they are reading or writing a workbook. It is also thetop level object for creating new sheets/etc
	 * @return constructed cell style with alignment center
	 */
	static CellStyle getCenterAlignCellStyle(Workbook workbooks) {
		CellStyle cellStyle = workbooks.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}
	
	/**
	 * Responsible to set header styling for all sheet
	 * @param workbooks construct whether they are reading or writing a workbook. It is also thetop level object for creating new sheets/etc
	 * @return constructed cell style with UTF-8
	 */
	static CellStyle getHeaderStyle(Workbook workbooks) {
		// Create a Font for styling header cells
		Font headerFont = workbooks.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 18);
		headerFont.setFontName("Microsoft Himalaya");

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbooks.createCellStyle();
		headerCellStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
		headerCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		//headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		return headerCellStyle;
	}
	
	/**
	 * Responsible to set header styling for merged cell of all sheet 
	 * @param workbooks construct whether they are reading or writing a workbook. It is also thetop level object for creating new sheets/etc
	 * @return constructed cell style with UTF-8
	 */
	static CellStyle getMergedCelHeaderStyle(Workbook workbooks) {
		// Create a Font for styling header cells
		Font headerFont = workbooks.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 18);
		headerFont.setFontName("Microsoft Himalaya");

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbooks.createCellStyle();
		headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		return headerCellStyle;
	}

	/**
	 * Responsible to format the java.util.date into dd-MM-yyyy
	 * @param workbooks construct whether they are reading or writing a workbook. It is also thetop level object for creating new sheets/etc
	 * @return constructed cell style with date format
	 */
	static CellStyle getDateFormatedCellStyle(Workbook workbooks) {
		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbooks.createCellStyle();
		dateCellStyle.setDataFormat(getCreationHelper(workbooks).createDataFormat().getFormat(DATE_FORMATE));
		return dateCellStyle;
	}

	/**
	 * CreationHelper helps us create instances of various things like DataFormat,Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
	 * @param workbooks construct whether they are reading or writing a workbook. It is also the top level object for creating new sheets/etc
	 * @return An object that handles instantiating concrete classes of the various instances one needs forHSSF and XSSF

	 */
	static CreationHelper getCreationHelper(Workbook workbooks) {
		CreationHelper createHelper = workbooks.getCreationHelper();
		return createHelper;
	}
}
