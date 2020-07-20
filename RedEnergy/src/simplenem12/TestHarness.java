// Copyright Red Energy Limited 2017

package simplenem12;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Simple test harness for trying out SimpleNem12Parser implementation
 */
public class TestHarness {

	public static void main(String[] args) {
		File simpleNem12File = new File(args[0]);
		// local dates for testing purpose
		LocalDate ld = LocalDate.parse("20161114", SimpleNem12ParserImpl.dtf);
		LocalDate nd = LocalDate.parse("20161221", SimpleNem12ParserImpl.dtf);
		
		Collection<MeterRead> meterReads = new SimpleNem12ParserImpl().parseSimpleNem12(simpleNem12File);

		MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
		System.out.println("******NMI 6123456789 Details -Start******");
		System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume())); // Should be -36.84																																																									// -36.84
		System.out.println("count of meter volumes for NMI 6123456789 is "
				+ meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).count());
		System.out.println("Meter volume on 20161114 is " + read6123456789.getMeterVolume(ld).getVolume()
				+ " and quality is " + read6123456789.getMeterVolume(ld).getQuality());
		System.out.println("Count of MeterVolumes having 'A' quality " + read6123456789.getVolumes().values().stream()
				.filter(q -> q.getQuality().equals(Quality.valueOf("A"))).count());
		System.out.println("******NMI 6123456789 Details -End******");

		System.out.println("");

		System.out.println("******NMI 6987654321 Details -Start******");
		MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
		System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume())); // Should be 14.33
		System.out.println("Count of MeterVolumes for NMI 6987654321 is "
				+ meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).count());
		System.out.println("Meter volume on 20161221 is " + read6987654321.getMeterVolume(nd).getVolume()
				+ " and quality is " + read6987654321.getMeterVolume(nd).getQuality());
		System.out.println("Count of MeterVolumes having 'E' quality " + read6987654321.getVolumes().values().stream()
				.filter(q -> q.getQuality().equals(Quality.valueOf("E"))).count());
		System.out.println("******NMI 6987654321 Details -End******");

	}
}
