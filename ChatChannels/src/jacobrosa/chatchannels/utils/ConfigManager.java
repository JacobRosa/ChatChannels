package jacobrosa.chatchannels.utils;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import jacobrosa.chatchannels.ChatChannels;

public class ConfigManager {
	
	private static final ConfigManager configuration = new ConfigManager("config");
    private File file;
      
    public static ConfigManager getConfig() {
    	return configuration; 
    }
    
    private FileConfiguration config;
    
    private ConfigManager(String fileName){
	    if (!ChatChannels.getPlugin().getDataFolder().exists()) {
	    	ChatChannels.getPlugin().getDataFolder().mkdir();
	    }
	    this.file = new File(ChatChannels.getPlugin().getDataFolder(), fileName + ".yml");
	      
	    if (!this.file.exists()) {
	        try {
	        this.file.createNewFile();
	        } catch (Exception e) {
	        e.printStackTrace();
	        }
	      }
	      
	    this.config = YamlConfiguration.loadConfiguration(this.file);
    }
    
    public void delete(){
		file.delete();
	}
    
    @SuppressWarnings("unchecked")
	public <T> T get(String path)
    {
    	return (T)this.config.get(path);
    }
    
    public <T> int getInt(String path) {
    	return this.config.getInt(path);
    }
	        
    public <T> FileConfiguration getFileConfiguration() {
    	return this.config;
    }
    
    public <T> double getDouble(String path) {
    	return this.config.getDouble(path);
    }
    
    public <T> String getString(String path) {
    	return this.config.getString(path);
    }
    
    public <T> boolean getBoolean(String path) {
    	return this.config.getBoolean(path);
    }
    
    public <T> List<String> getList(String path) {
    	return this.config.getStringList(path);
    }
    
    public <T> ItemStack getItemStack(String path) {
    	return this.config.getItemStack(path);
    }
    
    public void set(String path, Object value) {
    	this.config.set(path, value);
    	save();
    }
    
    public boolean contains(String path) {
    	return this.config.contains(path);
    }
    
    public ConfigurationSection createSection(String path) {
    	ConfigurationSection section = this.config.createSection(path);
    	save();
    	return section;
    }
    
    public void save() {
    	try {
    		this.config.save(this.file);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public Set<String> getKeys() {
    	return this.config.getKeys(false);
    }
    
    public void reset(String path) {
    	this.config.set(path, null);
    }
}
