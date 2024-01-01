package com.example.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType

@RestController
@RequestMapping("api/v1/article")
class ArticleConttroller {

    val articles = mutableListOf(Article("My Title", "my Content"))
    
    @GetMapping
    fun articles() = articles
}

// // 在 RestControllers.kt 中新增一個新的 Controller
// @RestController
// @RequestMapping("api/v1/image")
// class ImageController(private val imageService: ImageService) {

//     @GetMapping("/{id}")
//     fun getImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
//         val imageData = imageService.getImageDataById(id)
//         return ResponseEntity.ok()
//             .contentType(MediaType.IMAGE_JPEG)
//             .body(imageData)
//     }
// }

// // 創建一個 ImageService 來處理從 MariaDB 中取得圖片的邏輯
// @Service
// class ImageService(private val imageRepository: ImageRepository) {

//     fun getImageDataById(id: Long): ByteArray {
//         val image = imageRepository.findById(id)
//         // 在這裡實作從資料庫中取得圖片資料的邏輯
//         // 返回圖片的 byte array
//     }
// }

// // 創建一個 ImageRepository 來處理與 MariaDB 互動的邏輯
// @Repository
// interface ImageRepository : JpaRepository<Image, Long> {
//     // 在這裡可以定義取得圖片資料的方法
// }