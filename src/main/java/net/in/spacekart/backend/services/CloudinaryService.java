package net.in.spacekart.backend.services;

import java.util.Map;

public interface CloudinaryService {


    public Map<String, String> deleteFile(String public_id);

    public Map<Object, Object> uploadFile(byte[] file, String folderName);

    public void deleteFileAsync(String public_id);

}
