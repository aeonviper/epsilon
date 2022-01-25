package epsilon.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BaseModel {

	protected Long id;

	protected LocalDateTime created;
	protected Long creatorId;
	protected String creator;

	protected LocalDateTime edited;
	protected Long editorId;
	protected String editor;

	protected Map<String, Object> storageMap = new HashMap<>();
	protected String storageMapData;

	protected Map<String, Object> map = new HashMap<>();

	public Object get(String key) {
		if (map != null) {
			return map.get(key);
		}
		return null;
	}

	public Object set(String key, Object value) {
		if (map != null) {
			return map.put(key, value);
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public LocalDateTime getEdited() {
		return edited;
	}

	public void setEdited(LocalDateTime edited) {
		this.edited = edited;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Map<String, Object> getStorageMap() {
		return storageMap;
	}

	public void setStorageMap(Map<String, Object> storageMap) {
		this.storageMap = storageMap;
	}

	public String getStorageMapData() {
		return storageMapData;
	}

	public void setStorageMapData(String storageMapData) {
		this.storageMapData = storageMapData;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
