package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import test.Product;
import test.ProductRepository;
import test.Productout;
import test.Shop;
import test.Shopout;
import test.ShopRepository;
 
@RestController
public class GreetingController implements ErrorController {
	
	private static final String PATH="/error";
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	@Autowired
	private ProductRepository ProductRepository;
	@Autowired
	private ShopRepository ShopRepository;
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping (path = "/products")
	public @ResponseBody List<Productout> getAllProducts() {
		int size, i;
		List<Productout> productsout = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		ProductRepository.findAll().forEach(products::add);
		size = products.size();
		for (i=0; i<size; i++) {
			Productout productout = new Productout(products.get(i));
			productsout.add(productout);
		}
		return productsout;
	}
	
	@GetMapping("/products/{id}")
	public Product retrieveProduct(@PathVariable int id) {
		Optional<Product> product = ProductRepository.findById(id);

		if (!product.isPresent())
			throw new ProductNotFoundException("id-" + id);

		return product.get();
	}
	
	@PutMapping("/products/{id}")
	public String updateProduct(@PathVariable int id, @RequestParam String name, @RequestParam String description, @RequestParam String category,
			@RequestParam String tags) {
		Optional<Product> product = ProductRepository.findById(id);
		if (!product.isPresent())
			throw new ProductNotFoundException("id-" + id);
		product.get().setname(name);
		product.get().setdescription(description);
		product.get().setcategory(category);
		product.get().settags(tags);
		ProductRepository.save(product.get());
		return "Updated";
		
	}
	
	@PatchMapping("/products/{id}")
	public String semiupdateProduct(@PathVariable int id, @RequestParam Optional<String> name, @RequestParam Optional<String> description, @RequestParam Optional<String> category,
			@RequestParam Optional<String> tags) {
		Optional<Product> product = ProductRepository.findById(id);
		if (!product.isPresent())
			throw new ProductNotFoundException("id-" + id);
		if (name.isPresent()) {
			product.get().setname(name.get());
		}
		if (description.isPresent()) {
			product.get().setdescription(description.get());
		}
		if (category.isPresent()) {
			product.get().setcategory(category.get());
		}
		if (tags.isPresent()) {
			product.get().settags(tags.get());
		}
		ProductRepository.save(product.get());
		return "semiUpdated";
	}
	 
	@PostMapping (path = "/products")
	public @ResponseBody String addProduct(@RequestParam String name, @RequestParam String description, @RequestParam String category,
			@RequestParam String tags) {
		Product p = new Product();
		p.setname(name);
		p.setcategory(category);
		p.setdescription(description);
		p.settags(tags);
		p.setwithdrawn(0);
		ProductRepository.save(p);
		return "Done";
	}
	
	@GetMapping(path = "/shops")
	public @ResponseBody List<Shopout> getAllShops() {
		int size, i;
		List<Shopout> shopssout = new ArrayList<>();
		List<Shop> shops = new ArrayList<>();
		ShopRepository.findAll().forEach(shops::add);
		size = shops.size();
		for (i=0; i<size; i++) {
			Shopout shopout = new Shopout(shops.get(i));
			shopssout.add(shopout);
		}
		return shopssout;
	}
	
	
	@GetMapping("/shops/{id}")
	public Shop retrieveShop(@PathVariable int id) {
		Optional<Shop> shop = ShopRepository.findById(id);

		if (!shop.isPresent())
			throw new ShopNotFoundException("id-" + id);

		return shop.get();
	}
	
	@PutMapping("/shops/{id}")
	public String updateShop(@PathVariable int id, @RequestParam String name, @RequestParam String address, @RequestParam Double lng, 
			@RequestParam Double lat, @RequestParam String tags) {
		Optional<Shop> shop = ShopRepository.findById(id);
		if (!shop.isPresent())
			throw new ShopNotFoundException("id-" + id);
		shop.get().setname(name);
		shop.get().setaddress(address);
		shop.get().setlng(lng);
		shop.get().setlat(lat);
		shop.get().settags(tags);
		ShopRepository.save(shop.get());
		return "Updated";
	}
	
	@PatchMapping("/shops/{id}")
	public String semiupdateShop(@PathVariable int id, @RequestParam Optional<String> name, @RequestParam Optional<String> address, 
			@RequestParam Optional<Double> lng, @RequestParam Optional<Double> lat, @RequestParam Optional<String> tags) {
		Optional<Shop> shop = ShopRepository.findById(id);
		if (!shop.isPresent())
			throw new ShopNotFoundException("id-" + id);
		if(name.isPresent()) {
			shop.get().setname(name.get());
		}
		if(address.isPresent()) {
			shop.get().setaddress(address.get());
		}
		if(lng.isPresent()) {
			shop.get().setlng(lng.get());
		}
		if(lat.isPresent()) {
			shop.get().setlat(lat.get());
		}
		if(tags.isPresent()) {
			shop.get().settags(tags.get());
		}
		ShopRepository.save(shop.get());
		return "semiUpdated";
	}
	
	@PostMapping(path = "/shops") 
	public @ResponseBody String addShop(@RequestParam String name, @RequestParam String address, @RequestParam Double lng, 
			@RequestParam Double lat, @RequestParam String tags) {
		Shop s = new Shop();
		s.setname(name);
		s.setaddress(address);
		s.setlng(lng);
		s.setlat(lat);
		s.settags(tags);
		s.setwithdrawn(0);
		ShopRepository.save(s);
		return "Done";
	}
	
	@RequestMapping(value=PATH,method=RequestMethod.GET)
	public String defaultErrorMessage(){
		return "Requested Resource is not found!4!0!4!";
	}

	@Override
	public String getErrorPath() {
		return PATH;	
	}	
	
	
	
}