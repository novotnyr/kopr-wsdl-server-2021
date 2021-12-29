package sk.upjs.ics.kopr;

import org.example.parking.ParkingPortType;
import org.example.parking.ParkingRequest;
import org.example.parking.ParkingTicket;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebService
public class ParkingServiceImpl implements ParkingPortType {

	public ParkingTicket getTicket(ParkingRequest part) {
		return new ParkingTicket();
	}

	public static void main(String[] args) throws Exception {
		List<Source> metadata = new ArrayList<Source>();
		
		var wsdlSource = new StreamSource(ParkingServiceImpl.class.getResourceAsStream("/parking.wsdl"));
		wsdlSource.setSystemId("http://www.example.org/parking/parking.wsdl");
		metadata.add(wsdlSource);

		
		var xsdSource = new StreamSource(ParkingServiceImpl.class.getResourceAsStream("/parking.xsd"));
		xsdSource.setSystemId("http://www.example.org/parking/parking.xsd");
		metadata.add(xsdSource);
		
		
		var properties = new HashMap<String, Object>();
		properties.put(Endpoint.WSDL_SERVICE, new QName("http://www.example.org/parking", "ParkingServices"));
		properties.put(Endpoint.WSDL_PORT, new QName("http://www.example.org/parking", "ParkingService"));

		var endpoint = Endpoint.create(new ParkingServiceImpl());
		endpoint.setProperties(properties);
		endpoint.setMetadata(metadata);

		endpoint.publish("http://localhost:8888/parking");
	}

}
