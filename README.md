# Simple Calculator — Full CI/CD Pipeline

Proyek ini mendemonstrasikan **pipeline CI/CD lengkap** menggunakan **GitHub Actions**,
yang menerapkan keempat komponen yang diminta pada tugas:

| # | Komponen | Implementasi pada pipeline |
|---|----------|----------------------------|
| 1 | **Continuous Integration**  | Setiap `push` / `pull request` → kode dikompilasi otomatis (`mvn compile`) |
| 2 | **Continuous Testing**      | Unit test JUnit dijalankan otomatis + laporan coverage JaCoCo |
| 3 | **Continuous Inspection**   | Analisis statis: **SpotBugs** (deteksi bug) + **Checkstyle** (gaya kode) |
| 4 | **Continuous Delivery**     | Aplikasi dikemas menjadi JAR siap-deploy dan diunggah sebagai artifact |
| 4 | **Continuous Deployment**   | JAR otomatis dipublikasikan sebagai **GitHub Release** saat merge ke `main` |

> Kelompok 5 orang → **Continuous Integration tetap wajib** (tidak diabaikan).
> Pipeline dipecah menjadi **5 job**, satu job untuk satu anggota.

## Pembagian Tanggung Jawab Anggota

| Job di pipeline (`.github/workflows/ci-cd.yml`) | Komponen | Anggota |
|--------------------------------------------------|----------|---------|
| `continuous-integration` | Continuous Integration | **Anggota 1** |
| `continuous-testing`     | Continuous Testing     | **Anggota 2** |
| `continuous-inspection`  | Continuous Inspection  | **Anggota 3** |
| `continuous-delivery`    | Continuous Delivery    | **Anggota 4** |
| `continuous-deployment`  | Continuous Deployment  | **Anggota 5** |

> Ganti "Anggota 1..5" dengan nama asli masing-masing sebelum submit / rekam video.

## Aplikasi yang Dipipeline

Aplikasi `Calculator` sederhana (Java + Maven) dengan operasi:
`add`, `subtract`, `multiply`, `divide`, `modulo`, `power`.
Diuji oleh **9 unit test** JUnit (termasuk pengujian exception).

```
.
├── src/
│   ├── main/java/com/example/Calculator.java     # kode aplikasi
│   └── test/java/com/example/CalculatorTest.java # unit test (9 test)
├── config/checkstyle/checkstyle.xml              # aturan Checkstyle (inspection)
├── .github/workflows/ci-cd.yml                   # PIPELINE CI/CD (file utama)
├── pom.xml                                        # konfigurasi Maven + plugin
└── README.md
```

## Cara Kerja Pipeline (alur)

```
                 push / pull_request
                          │
                 ┌────────▼────────┐
                 │ 1. Integration  │  (mvn clean compile)
                 └───┬─────────┬───┘
                     │         │
        ┌────────────▼──┐  ┌───▼─────────────┐
        │ 2. Testing    │  │ 3. Inspection   │  (jalan paralel)
        │ (test+JaCoCo) │  │ (SpotBugs+Style)│
        └────────────┬──┘  └───┬─────────────┘
                     │         │
                 ┌───▼─────────▼───┐
                 │ 4. Delivery     │  (mvn package → JAR artifact)
                 └────────┬────────┘
                          │  (hanya jika push ke main)
                 ┌────────▼────────┐
                 │ 5. Deployment   │  (publish GitHub Release)
                 └─────────────────┘
```

`needs:` membuat setiap job menunggu job sebelumnya. Jika satu job gagal,
job berikutnya **tidak dijalankan** dan PR tidak bisa di-merge (jika branch
protection diaktifkan) — inilah inti dari "pipeline gate".

## Konfigurasi Penting

### Environment variables (`env:` di workflow)
- `JAVA_VERSION: '17'` — versi JDK yang dipakai semua job
- `JAVA_DISTRIBUTION: 'temurin'` — distribusi JDK (Eclipse Temurin)

### Trigger (`on:`)
- `push` ke `main` / `develop`
- `pull_request` ke `main`

### Permissions
- `contents: write` — diperlukan job Deployment untuk membuat GitHub Release
  (menggunakan `GITHUB_TOKEN` otomatis, **tanpa secret tambahan**).

## Menjalankan Secara Lokal (opsional, untuk demo)

Prasyarat: JDK 17+ dan Maven 3.8+.

```bash
mvn clean compile              # Integration  → kompilasi
mvn test                       # Testing      → 9 test + coverage JaCoCo
mvn clean compile spotbugs:check   # Inspection (bug)
mvn checkstyle:check           # Inspection (style)
mvn package                    # Delivery     → menghasilkan target/*.jar
```

Coverage report HTML: `target/site/jacoco/index.html`.

## Lisensi

Open source untuk keperluan tugas akademik (MKPL).
