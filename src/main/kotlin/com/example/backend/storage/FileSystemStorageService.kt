package com.example.backend.storage

import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile

@Service
class FileSystemStorageService @Autowired constructor(private val properties: StorageProperties) :
    StorageService {

    private val rootLocation: Path = Paths.get(properties.location)

    init {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation)
            }
            if (properties.location.trim { it <= ' ' }.isEmpty()) {
                throw StorageException("File upload location can not be Empty.")
            }
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun store(file: MultipartFile) {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }
            val destinationFile =
                rootLocation
                    .resolve(Paths.get(file.originalFilename!!))
                    .normalize()
                    .toAbsolutePath()
            if (!destinationFile.parent.equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw StorageException("Cannot store file outside current directory.")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(rootLocation, 1)
                .filter { path: Path -> path != rootLocation }
                .map { rootLocation.relativize(it) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw StorageFileNotFoundException("Could not read file: $filename")
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }
}
