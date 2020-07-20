package simplenem12;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleNem12ParserImpl implements SimpleNem12Parser {
	private static final Logger logger = Logger.getLogger(SimpleNem12ParserImpl.class.getName());

	private static final String FIRST_LINE = "100";
	private static final String LAST_LINE = "900";
	private static final String METER_START = "200";
	private static final String METER_VOLUME = "300";
	private static final String DELIMITER = ",";
	public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

	private MeterRead mr;
	private MeterVolume mv;
	private Collection<MeterRead> meterReads = new ArrayList<MeterRead>();

	@Override
	public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
	
		if (isValidInputFile(simpleNem12File)) {
		try (Stream<String> lines = Files.lines(simpleNem12File.toPath())) {
            lines.skip(1).forEach(line ->{
            	try {
            	String[] lineDetail = line.split(DELIMITER);
				if (lineDetail[0].equals(METER_START)) {
					mr = new MeterRead(lineDetail[1], EnergyUnit.valueOf(lineDetail[2]));
				} else if (lineDetail[0].equals(METER_VOLUME)) {
					mv = new MeterVolume(new BigDecimal(lineDetail[2]), Quality.valueOf(lineDetail[3]));
					if (mr != null) { 
						mr.appendVolume(LocalDate.parse(lineDetail[1], dtf), mv);
						meterReads.add(mr);
					} else {
						logger.log(Level.WARNING,
								"Skipping invalid  line ,Meter volume exists without Meter read ,contents are : '"
										+ line + "\'");
						throw new InvalidFileException("Meter volume exists without Meter read");
					}
				} else if (lineDetail[0].equals(LAST_LINE)) {
					// logger.info("End of the file reached" +line);
					return;
				} else {
					logger.log(Level.WARNING, "Skipping invalid or blank line ,contents are : '" + line + "\'");
					throw new InvalidFileException("File contains invalid data");
				}
            	
			} catch (InvalidFileException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
            });
		}catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		}
		return meterReads;
		
	}
	private boolean isValidInputFile(File simpleNem12File) {
		boolean isValid = false;
		try (Stream<String> lines = Files.lines(simpleNem12File.toPath())) {

			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			if (!values.isEmpty()) {
				String firstLine = values.get(0).get(0);
				String lastLine = values.get(values.size() - 1).get(0);
				isValid = (firstLine.equals(FIRST_LINE) && lastLine.equals(LAST_LINE));
				if (isValid) {
					return true;
				} else {
					throw new InvalidFileException("Invalid file,missing header/footer");
				}
			}

		} catch (IOException | InvalidFileException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return isValid;
	}
}
