package overcast.pgm.module.modules.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta; 

import com.google.common.base.Preconditions;

import overcast.pgm.module.modules.kits.parsers.BookKitParser;
import overcast.pgm.player.OvercastPlayer;

public class BookKit {

	protected final String title;
	protected final String author;
	protected final List<String> pages;

	private int slot = 0;

	// TODO
	public BookKit(String title, String author, int slot, List<String> pages) {
		Preconditions.checkNotNull(author, "the author can't be null");
		Preconditions.checkNotNull(title, "the title can't be null");
		this.title = title;
		this.author = author;
		this.slot = slot;
		this.pages = pages;
	}

	public BookKit(BookKitParser parser) {
		this(parser.getTitle(), parser.getAuthor(), parser.getSlot(), parser.getPages());
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getPages() {
		return pages;
	}

	public void apply(OvercastPlayer p, boolean force) {
		PlayerInventory inventory = p.getInventory();
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta) book.getItemMeta();
		bm.setTitle(this.title);
		bm.setAuthor(this.author);
		bm.setPages(this.pages);
		
		 
	
		book.setItemMeta(bm);

		if (inventory.getItem(slot) == null || force) {
			inventory.setItem(slot, book);
		} else {
			inventory.addItem(book);
		}
	}
}
