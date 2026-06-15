# Panduan Lengkap CI/CD — dari 0 sampai Submit

Repo: **https://github.com/jauharfz/simple-calculator-mkpl**

Dokumen ini menjelaskan **apa yang sudah jadi**, **apa yang harus kalian lakukan
manual** (rekam video + submit), dan **script demonstrasi** untuk tiap anggota.

---

## A. Status: yang SUDAH selesai (otomatis) ✅

| Item | Status |
|------|--------|
| Project Java + Maven (Calculator, 6 operasi) | ✅ dibuat |
| 9 unit test JUnit | ✅ lulus semua |
| Workflow CI/CD 5 job (`.github/workflows/ci-cd.yml`) | ✅ dibuat |
| Continuous Integration (compile) | ✅ jalan |
| Continuous Testing (test + coverage JaCoCo) | ✅ jalan |
| Continuous Inspection (SpotBugs + Checkstyle) | ✅ jalan |
| Continuous Delivery (package JAR + artifact) | ✅ jalan |
| Continuous Deployment (GitHub Release otomatis) | ✅ jalan, Release `v1.0.1` terbit |
| Repo di-push ke GitHub (public) | ✅ selesai |
| Pipeline pertama → **semua hijau** | ✅ sukses |

Cek sendiri:
- Pipeline: https://github.com/jauharfz/simple-calculator-mkpl/actions
- Release : https://github.com/jauharfz/simple-calculator-mkpl/releases

---

## B. Yang HARUS kalian lakukan MANUAL (tidak bisa diotomasi)

### B1. (WAJIB) Ganti nama "Anggota 1..5" dengan nama asli
Edit `README.md` dan komentar di `.github/workflows/ci-cd.yml`, ganti
`Anggota 1` … `Anggota 5` dengan nama 5 anggota kelompok. Lalu commit & push:

```bash
git add -A
git commit -m "docs: isi nama anggota kelompok"
git push
```

### B2. (OPSIONAL tapi BAGUS) Aktifkan Branch Protection
Supaya PR tidak bisa di-merge kalau pipeline gagal (memperkuat poin "gate"):
1. Buka repo → **Settings** → **Branches** → **Add branch ruleset** (atau "Add rule").
2. Branch name pattern: `main`.
3. Centang **Require status checks to pass before merging**.
4. Pilih checks: `1. Continuous Integration`, `2. Continuous Testing`,
   `3. Continuous Inspection`, `4. Continuous Delivery`.
5. Save.

> Ini lewat web GitHub, tidak bisa saya buatkan otomatis dari sini.

### B3. (WAJIB) Rekam video demonstrasi + upload ke YouTube/LMS
Lihat **bagian D** untuk script lengkap.

### B4. (WAJIB) Submit ke LMS
- URL repository: `https://github.com/jauharfz/simple-calculator-mkpl`
- URL video demonstrasi (YouTube/dll).

---

## C. Demonstrasi Pull Request (untuk video)

Pipeline berjalan pada **push** dan **pull request**. Untuk merekam alur PR
(termasuk "kenapa PR lolos / gagal cek"), gunakan branch `develop` yang
**sudah saya siapkan**.

### C1. Skenario PR LOLOS (happy path)
```bash
git checkout develop
git pull

# buat perubahan kecil yang valid, contoh tambah komentar atau method baru
# (silakan edit src/main/java/com/example/Calculator.java sedikit)

git add -A
git commit -m "feat: tambah perubahan kecil untuk demo PR"
git push
```
Lalu buka PR `develop` → `main`:
```bash
gh pr create --base main --head develop --title "Demo PR" --body "Demonstrasi pipeline"
```
Di tab **Pull requests**, tunjukkan:
- 4 cek berjalan (Integration, Testing, Inspection, Delivery) → **hijau**.
- Deployment **tidak** jalan di PR (memang sengaja: deploy hanya saat sudah merge ke main).
- Karena semua cek hijau → tombol **Merge** aktif. Klik merge.
- Setelah merge → push ke `main` → pipeline jalan lagi **lengkap + Deployment** →
  Release baru `v1.0.2` terbit otomatis.

### C2. Skenario PR GAGAL (untuk menjelaskan "kenapa gagal")
Buat perubahan yang **sengaja merusak test**, contoh ubah `add` jadi `a - b`
di `Calculator.java`, commit & push ke `develop`, buka PR. Tunjukkan:
- Job **Continuous Testing** jadi **merah** (test `testAdd` gagal).
- Job berikutnya (Delivery) **tidak jalan** karena `needs:` gagal.
- Tombol Merge diblokir (kalau branch protection aktif).
- **Kembalikan** perubahan (revert) supaya `develop` bersih lagi.

> Tips: rekam skenario gagal dulu, lalu perbaiki, supaya video menunjukkan
> pipeline "menangkap" bug — ini nilai plus.

---

## D. Script Video per Anggota

Total target durasi ~8–12 menit. Setiap anggota menjelaskan **bagian sendiri**
sambil menunjuk layar (file workflow + tab Actions).

**Pembuka (siapa saja, 1 menit)**
- Tunjukkan repo + struktur folder.
- Jelaskan: aplikasi Calculator Java+Maven, pipeline GitHub Actions 5 job untuk
  4 komponen CI/CD. Kelompok 5 orang → CI tetap wajib.

**Anggota 1 — Continuous Integration**
- Buka `.github/workflows/ci-cd.yml`, jelaskan `on:` (push & pull_request) dan
  `env:` (JAVA_VERSION, JAVA_DISTRIBUTION).
- Jelaskan job `continuous-integration`: checkout → setup JDK → `mvn clean compile`.
- Buka tab Actions, tunjuk job "1. Continuous Integration" hijau. Jelaskan
  artinya: kode dari semua anggota berhasil digabung & dikompilasi otomatis.

**Anggota 2 — Continuous Testing**
- Jelaskan job `continuous-testing`, `needs: continuous-integration` (jalan setelah build sukses).
- `mvn test` → 9 unit test JUnit + coverage JaCoCo.
- Tunjuk artifact **test-coverage-reports** (download → buka `index.html`).
- Jelaskan kenapa penting: setiap perubahan diuji otomatis, mencegah regresi.

**Anggota 3 — Continuous Inspection**
- Jelaskan job `continuous-inspection`: **SpotBugs** (deteksi bug, `failOnError` →
  bisa menggagalkan build) + **Checkstyle** (gaya kode, pakai `config/checkstyle/checkstyle.xml`).
- Tunjuk artifact **inspection-reports** (spotbugsXml.xml, checkstyle-result.xml).
- Jelaskan: menjaga kualitas kode secara terus-menerus, bukan sekadar "jalan".

**Anggota 4 — Continuous Delivery**
- Jelaskan job `continuous-delivery`, `needs: [testing, inspection]` → artifact
  hanya dibuat kalau test & inspeksi lolos ("selalu siap deploy").
- `mvn package` → `target/simple-calculator-1.0.0.jar` diunggah sebagai artifact
  **application-jar**. Tunjuk cara download JAR-nya.

**Anggota 5 — Continuous Deployment**
- Jelaskan job `continuous-deployment`, `needs: continuous-delivery` +
  `if: ref == main && event == push` → deploy **hanya** untuk kode yang sudah
  masuk `main`, bukan PR.
- `softprops/action-gh-release` + `permissions: contents: write` + `GITHUB_TOKEN`
  → publish **GitHub Release** otomatis dengan tag `v1.0.<run_number>` + JAR.
- Buka tab **Releases**, tunjuk `v1.0.1` beserta file JAR terlampir.

**Demonstrasi PR (siapa saja, gabungkan dengan bagian C)**
- Tunjukkan satu siklus PR lolos (dan opsional satu PR gagal + alasannya).

**Penutup**
- Ringkas: 4 komponen CI/CD semua terpenuhi & otomatis dari push → release.

---

## E. Cara cek/jalankan lokal (kalau ditanya saat demo)

```bash
mvn clean compile                  # Integration
mvn test                           # Testing (9 test) + coverage
mvn clean compile spotbugs:check   # Inspection (bug)
mvn checkstyle:check               # Inspection (style)
mvn package                        # Delivery (JAR di target/)
```

---

## F. Checklist Submit

- [ ] Nama 5 anggota sudah diisi di README & workflow (B1)
- [ ] (opsional) Branch protection aktif (B2)
- [ ] Pipeline di Actions menunjukkan semua job hijau
- [ ] Minimal 1 Release otomatis terbit di tab Releases
- [ ] Video demonstrasi (setup + proses CI/CD + per-anggota) selesai direkam
- [ ] Video di-upload (YouTube/dll), link siap
- [ ] Submit URL repo + URL video ke LMS sebelum deadline
