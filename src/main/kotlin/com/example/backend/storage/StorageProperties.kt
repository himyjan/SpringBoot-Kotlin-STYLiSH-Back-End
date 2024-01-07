package com.example.backend.storage

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("storage")
class StorageProperties {
    /** Folder location for storing files */
    private var _location: String = "src/main/kotlin/example/backend/assets"

    var location: String
        get() = _location
        set(newLocation: String) {
            _location = newLocation
        }
}
