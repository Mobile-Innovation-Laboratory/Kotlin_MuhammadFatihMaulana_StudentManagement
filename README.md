# Dokumentasi Fullstack: Backend Laravel & Frontend Android Jetpack Compose

Aplikasi ini merupakan contoh implementasi fullstack yang terdiri dari backend Laravel dan frontend Android menggunakan Jetpack Compose. Aplikasi ini memungkinkan pengguna untuk mengelola data siswa, melakukan autentikasi, dan menyimpan data ke daftar favorit.

## 1. Setup Backend Laravel

### 1.1. Menjalankan Laravel

Untuk menjalankan backend Laravel, ikuti langkah-langkah berikut:

1.  **Instal Laravel (Jika belum terinstal):**

    Jika Anda belum menginstal Laravel, instal secara global dengan Composer:

    ```bash
    composer global require laravel/installer
    ```

2.  **Clone Repository:**

    Clone repository backend dari GitHub:

    ```bash
    git clone [https://github.com/NoBody313/StudentManagement-API.git](https://github.com/NoBody313/StudentManagement-API.git)
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

| ![image](https://github.com/user-attachments/assets/b8b31aba-c515-47f0-a06e-cf57d099d27e) | ![image](https://github.com/user-attachments/assets/5ec4a98a-2cfb-4f0a-af96-4dc76d626ae4) | ![image](https://github.com/user-attachments/assets/f8beb1ae-c57b-43ef-9339-7976f07c6d74)
| :---: | :---: | :---: |
| ![image](https://github.com/user-attachments/assets/ba386d8c-53f9-4957-88c5-9b45cc587ed4) | ![image](https://github.com/user-attachments/assets/2c3b9d8e-4142-4e98-94bb-24e3d0013a32) | ![image](https://github.com/user-attachments/assets/bdc04515-0be2-45c9-a41a-b79cf57ae9f9)

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
