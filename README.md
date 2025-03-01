# Dokumentasi Fullstack: Backend Laravel & Frontend Android Jetpack Compose

Aplikasi ini merupakan contoh implementasi fullstack yang terdiri dari backend Laravel dan frontend Android menggunakan Jetpack Compose. Aplikasi ini memungkinkan pengguna untuk mengelola data siswa, melakukan autentikasi, dan menyimpan data ke daftar favorit.

## ✨ Fitur

### ✅ Manajemen Pengguna

Admin dapat menambahkan, mengedit, dan menghapus pengguna.

Role-based access untuk mengontrol fitur yang bisa diakses oleh masing-masing peran.


### ✅ Manajemen Data Akademik

- Guru dapat menginput nilai dan absensi siswa.
- Siswa dapat melihat jadwal, nilai, dan absensi mereka.

### ✅ Autentikasi & Otorisasi

- Sistem login dengan validasi berbasis peran.
- Middleware untuk membatasi akses fitur berdasarkan peran.

### ✅ Dashboard Interaktif

- Tampilan UI yang intuitif untuk masing-masing peran (Admin, Teacher, dan Student).

## 1. Setup Backend Laravel

### 1.1. Menjalankan Laravel

Link Guide ```https://drive.google.com/file/d/1ESD0gGXJmZiAfhUs4LVieROqP-OzIsCa/view?usp=sharing```

Untuk menjalankan backend Laravel, ikuti langkah-langkah berikut:

1.  **Instal Laravel (Jika belum terinstal):**

    Jika Anda belum menginstal Laravel, instal secara global dengan Composer:

    ```bash
    composer global require laravel/installer
    ```

2.  **Clone Repository:**

    Clone repository backend dari GitHub:

    ```bash
    git clone https://github.com/NoBody313/StudentManagement-API.git
    ```

3.  **Install Dependencies:**

    Masuk ke folder proyek dan instal dependensi:

    ```bash
    cd StudentManagement-API
    composer install
    ```

4.  **Konfigurasi Environment:**

    Salin file `.env.example` ke `.env`:

    ```bash
    cp .env.example .env
    ```

    Atur konfigurasi database, mail, dan lainnya sesuai kebutuhan di file `.env`. Pastikan konfigurasi database sesuai dengan database yang Anda gunakan (MySQL, PostgreSQL, dll.).

5.  **Menjalankan Migrate:**

    Jalankan migration untuk membuat tabel database:

    ```bash
    php artisan migrate
    ```

6.  **Menjalankan generate JWT Secret:**

    Generate JWT secret key untuk autentikasi:

    ```bash
    php artisan jwt:secret
    ```

7.  **Menjalankan Server Laravel:**

    Jalankan server Laravel dengan opsi host dan port. Ganti `192.168.220.95` dengan IP lokal komputer Anda jika berbeda. Pastikan komputer dan perangkat Android berada dalam jaringan yang sama.

    ```bash
    php artisan serve --host=192.168.220.95 --port=8000
    ```

    Server Laravel akan berjalan pada `http://192.168.220.95:8000`.

## 2. Pengaturan URL di Android (Retrofit & Network Security)

### 2.1. Retrofit Configuration

Konfigurasikan Retrofit untuk mengarah ke URL server Laravel yang berjalan pada host dan port yang telah Anda tentukan.

**Kode Retrofit Instance:**

```kotlin
object RetrofitInstance {
    private const val BASE_URL = "[http://192.168.220.95:8000/](http://192.168.220.95:8000/)"

    fun getRetrofitInstance(context: Context): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
```

Pastikan `BASE_URL` sesuai dengan host dan port yang digunakan di Laravel. Ganti `192.168.220.95` dengan IP lokal komputer Anda jika berbeda.

### 2.2. Network Security Configuration

Ubah konfigurasi di `res/xml/network_security_config.xml` untuk mengizinkan koneksi ke server lokal yang menggunakan HTTP.

**`network_security_config.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">192.168.220.95</domain>
    </domain-config>
</network-security-config>
```

Pastikan untuk mengganti `192.168.220.95` dengan IP lokal Anda jika berbeda.

## 3. API Endpoint di Backend Laravel

Berikut adalah daftar endpoint API yang dapat digunakan untuk berinteraksi dengan aplikasi:

### 3.1. API untuk Autentikasi

*   **`POST /api/login`:** Login pengguna.

    **Body Request:**

    ```json
    {
        "email": "johndoe@example.com",
        "password": "password123"
    }
    ```

    **Response:**

    ```json
    {
        "access_token": "your_generated_token_here"
    }
    ```

*   **`POST /api/register`:** Registrasi pengguna baru.

    **Body Request:**

    ```json
    {
        "name": "John Doe",
        "email": "johndoe@example.com",
        "password": "password123",
        "password_confirmation": "password123"
    }
    ```

### 3.2. API untuk Mengelola Data Siswa

*   **`GET /api/students`:** Mendapatkan daftar semua siswa.

    **Response:**

    ```json
    [
        {
            "id": 1,
            "name": "Student Name",
            "email": "student@example.com",
            "age": 20
            // ... (field lainnya)
        }
    ]
    ```

*   **`GET /api/students/{id}`:** Mendapatkan data siswa berdasarkan ID.

    **Response:**

    ```json
    {
        "id": 1,
        "name": "Student Name",
        "email": "student@example.com",
        "age": 20
        // ... (field lainnya)
    }
    ```

*   **`POST /api/students`:** Menambah siswa baru.

    **Body Request:**

    ```json
    {
        "name": "New Student",
        "age": 21,
        "email": "newstudent@example.com"
        // ... (field lainnya)
    }
    ```

    **Response:**

    ```json
    {
        "id": 2,
        "name": "New Student",
        "email": "newstudent@example.com",
        "age": 21
        // ... (field lainnya)
    }
    ```

*   **`PUT /api/students/{id}`:** Mengubah data siswa berdasarkan ID.

    **Body Request:**

    ```json
    {
        "name": "Updated Student",
        "age": 22,
        "email": "updatedstudent@example.com"
        // ... (field lainnya)
    }
    ```

    **Response:**

    ```json
    {
        "id": 2,
        "name": "Updated Student",
        "email": "updatedstudent@example.com",
        "age": 22
        // ... (field lainnya)
    }
    ```

*   **`DELETE /api/students/{id}`:** Menghapus siswa berdasarkan ID.

    **Response:**

    ```json
    {
        "message": "Student deleted successfully"
    }
    ```

## 4. Testing API dengan Postman

Gunakan Postman untuk menguji API yang telah Anda buat di Laravel.

### 4.1. Registrasi Pengguna

*   **Endpoint:** `POST /api/register`

    **Body Request:**

    ```json
    {
        "name": "John Doe",
        "email": "johndoe@example.com",
        "password": "password123",
        "password_confirmation": "password123"
    }
    ```

### 4.2. Login Pengguna

Setelah berhasil registrasi, lakukan login untuk mendapatkan token.

*   **Endpoint:** `POST /api/login`

    **Body Request:**

    ```json
    {
        "email": "johndoe@example.com",
        "password": "password123"
    }
    ```

    **Response:**

    ```json
    {
        "access_token": "your_generated_token_here"
    }
    ```

### 4.3. Mendapatkan Data Pengguna

Gunakan token yang didapatkan dari login untuk mengakses endpoint berikut.

*   **Endpoint:** `GET /api/user`

    **Headers:**

    ```
    Authorization: Bearer your_generated_token_here
    ```

    **Response:**

    ```json
    {
        "id": 1,
        "name": "John Doe",
        "email": "johndoe@example.com"
    }
    ```

### 4.4. Mendapatkan Daftar Siswa

Gunakan token yang sama untuk mengakses daftar siswa.

*   **Endpoint:** `GET /api/students`

    **Headers:**

    ```
    Authorization: Bearer your_generated_token_here
    ```

    **Response:**

    ```json
    [
        {
            "id": 1,
            "name": "Student Name",
            "email": "student@example.com"
            // ... (field lainnya)
        }
    ]
    ```

## 5. Jalankan Aplikasi di Android Studio

Setelah semua konfigurasi selesai, jalankan aplikasi di Android Studio:

1.  **Pilih Perangkat:** Pilih perangkat emulator atau perangkat fisik yang ingin digunakan.
2.  **Emulator:** Jika Anda menggunakan emulator, pastikan emulator sudah berjalan.
3.  **Perangkat Fisik:** Jika menggunakan perangkat fisik, pastikan perangkat terhubung dengan baik ke Android Studio melalui kabel USB dan telah mengaktifkan opsi **Developer Mode** serta **USB Debugging**.
4.  **Jalankan Aplikasi:** Klik tombol **Run** (tanda segitiga hijau) di Android Studio untuk menjalankan aplikasi.
5.  **Pilih Perangkat:** Anda bisa memilih perangkat yang terhubung di pop-up yang muncul setelah Anda menekan tombol **Run**.

Aplikasi Android akan terhubung ke server Laravel dan melakukan request API menggunakan Retrofit.

## 6. Fitur Aplikasi

Aplikasi ini memiliki beberapa fitur utama, yaitu:

*   **Autentikasi:** Pengguna dapat melakukan registrasi dan login untuk mengakses fitur-fitur aplikasi.
*   **Manajemen Data Siswa:** Pengguna dapat melihat daftar siswa, melihat detail siswa, menambah siswa baru, mengubah data siswa, dan menghapus siswa.
*   **Fitur Favorite:**
    *   **Tambah Favorite:** Memungkinkan pengguna untuk menyimpan data siswa ke daftar Favorite.
        *   **Alur Tampilan:**
            1.  Pengguna melihat daftar siswa di layar utama.
            2.  Di setiap item siswa, terdapat ikon hati (atau ikon lain yang sesuai).
            3.  Pengguna dapat menekan ikon hati tersebut untuk menandai siswa sebagai favorit.
            4.  Ikon hati akan berubah (misalnya, menjadi berwarna atau terisi) untuk menunjukkan bahwa siswa tersebut telah ditambahkan ke favorit.
        *   **Alur Teknis:**
            1.  Ketika pengguna menekan ikon hati, aplikasi akan memanggil fungsi `onFavoriteChanged` di `StudentListItem`.
            2.  Fungsi ini akan menerima parameter `newIsFavorite` yang menunjukkan status favorit yang baru (true atau false).
            3.  Aplikasi akan memanggil `studentRepository.updateFavoriteStatus(student.id, newIsFavorite)` untuk memperbarui status favorit di database lokal (Room).
            4.  Aplikasi akan memperbarui daftar `favoriteStudents` dengan data terbaru dari database.
            5.  UI akan diperbarui untuk mencerminkan perubahan status favorit.
    *   **Lihat Favorite:** Menampilkan daftar siswa Favorite yang telah disimpan.
        *   **Alur Tampilan:**
            1.  Pengguna dapat mengakses layar "Favorite" melalui menu navigasi (misalnya, bottom navigation).
            2.  Layar "Favorite" akan menampilkan daftar siswa yang telah ditandai sebagai favorit.
            3.  Setiap item siswa akan menampilkan informasi siswa dan ikon hati yang sudah terisi.
        *   **Alur Teknis:**
            1.  Ketika pengguna membuka layar "Favorite", aplikasi akan mengambil data siswa favorit dari database lokal (Room) menggunakan `studentDao.getAllStudents()`.
            2.  Aplikasi akan memfilter data siswa dari API yang memiliki `isFavorite` true.
            3.  Data siswa favorit tersebut akan ditampilkan di layar "Favorite".
    *   **Hapus Favorite:** Memungkinkan pengguna untuk menghapus data siswa dari daftar Favorite.
        *   **Alur Tampilan:**
            1.  Pengguna melihat daftar siswa di layar "Favorite".
            2.  Di setiap item siswa, terdapat ikon hati yang sudah terisi.
            3.  Pengguna dapat menekan ikon hati tersebut untuk menghapus siswa dari favorit.
            4.  Ikon hati akan berubah (misalnya, menjadi tidak berwarna atau kosong) untuk menunjukkan bahwa siswa tersebut telah dihapus dari favorit.
        *   **Alur Teknis:**
            1.  Ketika pengguna menekan ikon hati, aplikasi akan memanggil fungsi `onFavoriteChanged` di `StudentListItem`.
            2.  Fungsi ini akan menerima parameter `newIsFavorite` yang menunjukkan status favorit yang baru (false).
            3.  Aplikasi akan memanggil `studentRepository.updateFavoriteStatus(student.id, newIsFavorite)` untuk memperbarui status favorit di database lokal (Room).
            4.  Aplikasi akan memperbarui daftar `favoriteStudents` dengan data terbaru dari database.
            5.  UI akan diperbarui untuk mencerminkan perubahan status favorit.

## 7. Tampilan Aplikasi

Untuk memberikan gambaran lebih jelas mengenai tampilan aplikasi, berikut adalah beberapa gambar yang menunjukkan antarmuka aplikasi Android yang terhubung dengan backend Laravel.

*   **Tampilan Login**
*   **Tampilan Dashboard**
*   **Tampilan Profile**
*   **Tampilan Daftar Siswa**
*   **Tampilan Daftar Favorite**

| ![WhatsApp Image 2025-03-02 at 00 13 28_7e7c25d3](https://github.com/user-attachments/assets/148f3029-9b8c-445a-9711-cf8554740ba9) | ![WhatsApp Image 2025-03-02 at 00 05 39_840194c6](https://github.com/user-attachments/assets/20cf4713-080b-402a-b09e-0156e36a0092) | ![WhatsApp Image 2025-03-02 at 00 05 39_7f7ae1e6](https://github.com/user-attachments/assets/5a905473-b7ec-4557-9547-e4e31dfbfeeb) |
|:---:|:---:|:---:|
| ![WhatsApp Image 2025-03-02 at 00 05 40_c62de848](https://github.com/user-attachments/assets/599ec8ae-1382-41c7-91d6-55c6244e4dea) | ![WhatsApp Image 2025-03-02 at 00 05 40_8a88d930](https://github.com/user-attachments/assets/1290edf2-2274-445b-bea6-3bdde2bb7fb2) | ![WhatsApp Image 2025-03-02 at 00 05 40_4e34fc86](https://github.com/user-attachments/assets/7e0f60fe-d9a3-450e-b9ef-880b3607cdbc) |
| ![WhatsApp Image 2025-03-02 at 00 05 41_a70d6cf6](https://github.com/user-attachments/assets/53a0ecc6-b780-4932-a52d-605cd5b9ea67) | ![WhatsApp Image 2025-03-02 at 00 05 41_622a8406](https://github.com/user-attachments/assets/513ac3a3-3ed5-47e7-8ff8-8c34d1ddeace) | ![WhatsApp Image 2025-03-02 at 00 05 41_b21627d7](https://github.com/user-attachments/assets/c79f89e3-66c4-4245-85e3-0749f8bad6e7) |
| ![WhatsApp Image 2025-03-02 at 00 05 41_0b79309d](https://github.com/user-attachments/assets/9ca045c4-b823-4c93-8f18-d38f5698da68) | ![WhatsApp Image 2025-03-02 at 00 05 42_6471583f](https://github.com/user-attachments/assets/e8f5daf6-345d-4fe6-8b8b-d4636641a7aa) | ![WhatsApp Image 2025-03-02 at 00 05 42_0a957103](https://github.com/user-attachments/assets/5428500c-7441-4372-94f5-10347f9030cd) |
| ![WhatsApp Image 2025-03-02 at 00 05 42_5b56d85f](https://github.com/user-attachments/assets/42d26b6a-bf66-4dfe-be34-781a2d903600) | ![WhatsApp Image 2025-03-02 at 00 05 43_d5ceb8ef](https://github.com/user-attachments/assets/5b911733-574c-438c-ac2e-d2c4a01b55cb) | ![WhatsApp Image 2025-03-02 at 00 05 44_f76497b7](https://github.com/user-attachments/assets/59068439-d61a-489a-8331-2851db2c60f2) |
| ![WhatsApp Image 2025-03-02 at 00 05 44_9aa0219a](https://github.com/user-attachments/assets/f8f7a376-701c-448a-940b-f283219acd58) | ![WhatsApp Image 2025-03-02 at 00 05 44_3bff9d6c](https://github.com/user-attachments/assets/9b90afc9-c2bd-49a7-8311-03ad6cbd28ae) | ![WhatsApp Image 2025-03-02 at 00 05 44_9df01ebb](https://github.com/user-attachments/assets/3b6aa2e2-9061-4e87-976d-dc899a0475e5) |
| ![WhatsApp Image 2025-03-02 at 00 05 45_82394c4f](https://github.com/user-attachments/assets/bba9ab02-76f3-4b32-a503-18573f5c03e0) | ![WhatsApp Image 2025-03-02 at 00 05 45_bb80247a](https://github.com/user-attachments/assets/5407e153-5dc2-490e-a621-955dc8aa6b83) | ![WhatsApp Image 2025-03-02 at 00 05 45_f1af22a5](https://github.com/user-attachments/assets/fbd398dc-2e3f-46df-9de6-447ec47ad866) |
| ![WhatsApp Image 2025-03-02 at 00 05 45_0b7fb697](https://github.com/user-attachments/assets/6ddc4ce8-4da6-4621-835b-3a441a2100ec) | ![WhatsApp Image 2025-03-02 at 00 05 46_208b0469](https://github.com/user-attachments/assets/0e541e25-08a6-4486-92b5-ed702d9c2b4c) | ![WhatsApp Image 2025-03-02 at 00 05 46_ca374c90](https://github.com/user-attachments/assets/fcaa8b97-3786-4af1-b971-9601b5b0ce47) |
| ![WhatsApp Image 2025-03-02 at 00 05 46_94bcc544](https://github.com/user-attachments/assets/445a467e-74a7-4c04-bb49-4cfe137092db) |  |  |


Dengan mengikuti langkah-langkah ini, aplikasi Android Anda akan dijalankan dan berkomunikasi dengan server backend Laravel.

## 8. Catatan Tambahan

*   **IP Lokal:** Pastikan untuk mengganti `192.168.220.95` dengan IP lokal komputer Anda di semua konfigurasi (Laravel, Retrofit, dan Network Security).
*   **Jaringan:** Pastikan komputer dan perangkat Android berada dalam jaringan yang sama.
*   **Database:** Pastikan konfigurasi database di file `.env` sudah benar dan sesuai dengan database yang Anda gunakan.
*   **JWT Secret:** Pastikan Anda sudah menjalankan `php artisan jwt:secret` untuk generate JWT secret key.
*   **Postman:** Gunakan Postman untuk menguji semua endpoint API sebelum menjalankan aplikasi Android.
*   **Error Handling:** Jika terjadi error, periksa log di Android Studio dan log di server Laravel untuk mencari tahu penyebabnya.
*   **Model dan Entity:** Pastikan model dan entity yang kamu gunakan sudah sesuai dengan struktur data yang ada di database.
*   **Mapper:** Pastikan mapper yang kamu gunakan sudah sesuai dengan model dan entity yang kamu gunakan.
---
