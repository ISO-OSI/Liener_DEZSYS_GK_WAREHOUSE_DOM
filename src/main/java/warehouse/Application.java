package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;

@SpringBootApplication
@RestController
public class Application implements CommandLineRunner {

	@Autowired
	private WarehouseRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Initialize product data repository
		repository.deleteAll();

		// save product data for one warehouse
		repository.save(new ProductData("1","00-443175","Bio Orangensaft Sonne","Getraenk", 2500));
		repository.save(new ProductData("1","00-871895","Bio Apfelsaft Gold","Getraenk", 3420));
		repository.save(new ProductData("1","00-112233","Mineralwasser Still","Getraenk", 1200));
		repository.save(new ProductData("1","00-445566","Cola Mix","Getraenk", 860));
		repository.save(new ProductData("1","01-926885","Ariel Waschmittel Color","Waschmittel", 478));
		repository.save(new ProductData("1","01-111222","Persil Universal Pulver","Waschmittel", 310));
		repository.save(new ProductData("1","01-333444","Weichspueler Sommer","Waschmittel", 220));
		repository.save(new ProductData("1","02-234811","Mampfi Katzenfutter Rind","Tierfutter", 1324));
		repository.save(new ProductData("1","02-555666","Hundetrockenfutter Plus","Tierfutter", 640));
		repository.save(new ProductData("1","02-777888","Vogelkoerner Mix","Tierfutter", 90));
		System.out.println();

		// fetch all products
		System.out.println("ProductData found with findAll():");
		System.out.println("-------------------------------");
		for (ProductData productdata : repository.findAll()) {
			System.out.println(productdata);
		}
		System.out.println();

		// Fetch single product
		System.out.println("Record(s) found with ProductID(\"00-871895\"):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByProductID("00-871895"));
		System.out.println();

		// Fetch all products of Warehouse 1
		System.out.println("Record(s) found with findByWarehouseID(\"1\"):");
		System.out.println("--------------------------------");
		for (ProductData productdata : repository.findByWarehouseID("1")) {
			System.out.println(productdata);
		}
		System.out.println();

	}

	@GetMapping("/product")
	public List<ProductData> getProducts() {
		return repository.findAll();
	}

	@PostMapping("/product")
	public ProductData addProduct(@RequestBody ProductData productData) {
		if (productData.getWarehouseID() == null) {
			productData.setWarehouseID("1");
		}
		return repository.save(productData);
	}

	@GetMapping("/warehouse")
	public Map<String, Object> getWarehouse() {
		Map<String, Object> warehouse = new LinkedHashMap<>();
		warehouse.put("warehouseID", "1");
		warehouse.put("warehouseName", "Linz Warehouse");
		warehouse.put("warehouseCity", "Linz");
		warehouse.put("productData", repository.findByWarehouseID("1"));
		return warehouse;
	}
}
