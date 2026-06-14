package data;

import model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class DataStore {
    private static DataStore instance;

    private Map<String, User> users = new LinkedHashMap<>();
    private Map<String, Sesi> sesiMap = new LinkedHashMap<>();
    private Map<String, Tagihan> tagihanMap = new LinkedHashMap<>();
    private Map<String, ProgramLatihan> programMap = new LinkedHashMap<>();
    private Map<String, RekamMedis> rekamMedisMap = new LinkedHashMap<>();
    private Map<String, Notifikasi> notifikasiMap = new LinkedHashMap<>();

    private int counterSesi = 100;
    private int counterTagihan = 80;
    private int counterRekam = 50;
    private int counterNotif = 20;

    private DataStore() { initData(); }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    
    public String generateIdSesi()     { return "S-" + (++counterSesi); }
    public String generateIdTagihan()  { return "INV-2026-" + String.format("%04d", ++counterTagihan); }
    public String generateIdRekam()    { return "RM-" + (++counterRekam); }
    public String generateIdNotif()    { return "N-" + (++counterNotif); }

    
    private void initData() {
        
        Admin admin = new Admin("A001", "Andika Rizky", "admin@physiohub.com", "admin123",
                "0812-0000-0001", "Manajer Klinik");
        users.put(admin.getId(), admin);

        // --- TERAPIS ---
        Terapis t1 = new Terapis("T001", "Dr. Cut Eka", "cut@physiohub.com", "terapis123",
                "0812-1111-0001", "STR-12345-2022", "SIP-KOT-0892-2023",
                "Neurologi Rehab", 5);
        t1.setRating(4.9); t1.setStatus(Terapis.StatusTerapis.SIBUK);
        t1.tambahSertifikasi("WCPT Member"); t1.tambahSertifikasi("Certified NDT");
        users.put(t1.getId(), t1);

        Terapis t2 = new Terapis("T002", "Dr. Frengki", "frengki@physiohub.com", "terapis123",
                "0812-1111-0002", "STR-67890-2020", "SIP-KOT-1234-2022",
                "Muskuloskeletal", 7);
        t2.setRating(4.8); t2.setStatus(Terapis.StatusTerapis.TERSEDIA);
        t2.tambahSertifikasi("Certified Manual Therapist"); t2.tambahSertifikasi("McKenzie Method");
        users.put(t2.getId(), t2);

        Terapis t3 = new Terapis("T003", "Dr. Afrisca", "fica@physiohub.com", "terapis123",
                "0812-1111-0003", "STR-11111-2023", "SIP-KOT-5678-2024",
                "Sports Rehab", 3);
        t3.setRating(4.7); t3.setStatus(Terapis.StatusTerapis.ISTIRAHAT);
        t3.tambahSertifikasi("Sports Physiotherapy Cert."); t3.tambahSertifikasi("Kinesio Taping");
        users.put(t3.getId(), t3);

        
        Pasien p1 = new Pasien("P001", "Tabita", "bita@email.com", "pasien123",
                "0812-3456-7890", LocalDate.of(1980, 3, 15), "Laki-laki",
                "Jl. Merdeka No.12, Malang", "Low Back Pain", "BPJS Kesehatan");
        p1.setNomorAsuransi("0001234567890"); p1.setGolonganDarah("O");
        p1.setAlergi("Tidak ada"); p1.setTerapisId("T001");
        p1.setTotalSesiTarget(10); p1.setTotalSesiSelesai(5);
        p1.setProgresKekuatan(70); p1.setProgresFleksibilitas(55);
        p1.setProgresNyeri(65); p1.setProgresKemandirian(80);
        p1.setStatusPasien(Pasien.StatusPasien.AKTIF);
        p1.tambahRiwayatPenyakit("Hipertensi ringan (2020)");
        p1.tambahRiwayatPenyakit("Operasi lutut kiri (2018)");
        users.put(p1.getId(), p1);

        Pasien p2 = new Pasien("P002", "Risma", "risma@email.com", "pasien123",
                "0813-2345-6789", LocalDate.of(1987, 7, 22), "Perempuan",
                "Jl. Kenanga No.5, Malang", "Rehabilitasi Lutut Pasca-Op", "Umum");
        p2.setGolonganDarah("A"); p2.setAlergi("Penisilin");
        p2.setTerapisId("T002"); p2.setTotalSesiTarget(12); p2.setTotalSesiSelesai(3);
        p2.setProgresKekuatan(30); p2.setProgresFleksibilitas(25);
        p2.setProgresNyeri(35); p2.setProgresKemandirian(20);
        p2.setStatusPasien(Pasien.StatusPasien.AKTIF);
        users.put(p2.getId(), p2);

        Pasien p3 = new Pasien("P003", "Edinho", "edi@email.com", "pasien123",
                "0856-9876-5432", LocalDate.of(1963, 11, 5), "Laki-laki",
                "Jl. Sudirman No.88, Malang", "Stroke Rehabilitasi", "BPJS Kesehatan");
        p3.setNomorAsuransi("0009876543210"); p3.setGolonganDarah("B");
        p3.setAlergi("Aspirin"); p3.setTerapisId("T003");
        p3.setTotalSesiTarget(20); p3.setTotalSesiSelesai(8);
        p3.setProgresKekuatan(42); p3.setProgresFleksibilitas(38);
        p3.setProgresNyeri(50); p3.setProgresKemandirian(35);
        p3.setStatusPasien(Pasien.StatusPasien.AKTIF);
        users.put(p3.getId(), p3);

        Pasien p4 = new Pasien("P004", "Pebi", "pebi@email.com", "pasien123",
                "0821-4567-8901", LocalDate.of(1996, 4, 18), "Perempuan",
                "Jl. Gajahmada No.33, Malang", "Neck Pain Kronis", "Umum");
        p4.setGolonganDarah("AB"); p4.setAlergi("Tidak ada");
        p4.setTerapisId("T001"); p4.setTotalSesiTarget(8); p4.setTotalSesiSelesai(2);
        p4.setProgresKekuatan(28); p4.setProgresFleksibilitas(22);
        p4.setProgresNyeri(30); p4.setProgresKemandirian(25);
        p4.setStatusPasien(Pasien.StatusPasien.AKTIF);
        users.put(p4.getId(), p4);

        Pasien p5 = new Pasien("P005", "Fuuzan", "zan@email.com", "pasien123",
                "0857-1234-0000", LocalDate.of(2001, 9, 30), "Laki-laki",
                "Jl. Veteran No.7, Malang", "Cedera Ligamen Lutut", "Umum");
        p5.setGolonganDarah("O"); p5.setAlergi("Tidak ada");
        p5.setTerapisId("T002"); p5.setTotalSesiTarget(6); p5.setTotalSesiSelesai(0);
        p5.setStatusPasien(Pasien.StatusPasien.BARU);
        users.put(p5.getId(), p5);

        
        LocalDate today = LocalDate.now();
        buatSesi("S001","P001","T001",today,LocalTime.of(8,0),60,"Ruang 1","Low Back Pain",5,Sesi.StatusSesi.SELESAI);
        buatSesi("S002","P002","T002",today,LocalTime.of(9,30),90,"Ruang 2","Rehab Lutut",3,Sesi.StatusSesi.BERLANGSUNG);
        buatSesi("S003","P003","T003",today,LocalTime.of(11,0),60,"Ruang 3","Stroke Rehab",8,Sesi.StatusSesi.MENUNGGU);
        buatSesi("S004","P004","T001",today,LocalTime.of(13,0),45,"Ruang 1","Neck Pain",2,Sesi.StatusSesi.TERJADWAL);
        buatSesi("S005","P005","T002",today,LocalTime.of(15,30),60,"Ruang 2","Cedera Ligamen",1,Sesi.StatusSesi.TERJADWAL);
        buatSesi("S006","P002","T003",today.plusDays(1),LocalTime.of(8,0),60,"Ruang 3","Frozen Shoulder",6,Sesi.StatusSesi.TERJADWAL);
        buatSesi("S007","P001","T001",today.plusDays(7),LocalTime.of(8,30),60,"Ruang 1","Low Back Pain",6,Sesi.StatusSesi.TERJADWAL);
        buatSesi("S008","P003","T003",today.plusDays(2),LocalTime.of(11,0),60,"Ruang 3","Stroke Rehab",9,Sesi.StatusSesi.TERJADWAL);

        // --- REKAM MEDIS ---
        RekamMedis rm1 = new RekamMedis("RM001","P001","T001","S001",today.minusDays(5),4);
        rm1.isiSOAP("Nyeri punggung bawah VAS 5/10. Kaku saat pagi berkurang.",
                "ROM Lumbar Fleksi 55°. SLR (-). MMT ekstensor 3+/5.",
                "Perbaikan ROM. Kekuatan belum optimal. Target 60%.",
                "Lanjut core stabilization. Tambah latihan McKenzie.");
        rm1.setSkalaNyeriVAS(5); rm1.setTekananDarah("130/85"); rm1.setNadiPerMenit(78);
        rekamMedisMap.put(rm1.getId(), rm1);

        RekamMedis rm2 = new RekamMedis("RM002","P001","T001","S001",today,5);
        rm2.isiSOAP("Nyeri punggung bawah VAS 4/10. Kaku berkurang signifikan.",
                "ROM Lumbar Fleksi 60°. SLR (-). MMT ekstensor 4/5. Spasme berkurang.",
                "Perbaikan progresif. Kekuatan inti meningkat. Target tercapai 70%.",
                "Lanjut McKenzie + core stabilization. Tambah propriosepsi. Evaluasi sesi 7.");
        rm2.setSkalaNyeriVAS(4); rm2.setTekananDarah("128/82"); rm2.setNadiPerMenit(76);
        rekamMedisMap.put(rm2.getId(), rm2);

        RekamMedis rm3 = new RekamMedis("RM003","P002","T002","S002",today.minusDays(7),2);
        rm3.isiSOAP("Nyeri lutut kiri VAS 6/10 saat menekuk.",
                "Edema lutut berkurang. ROM Fleksi 80°. Kekuatan quad 3/5.",
                "Masih fase awal. Perlu penguatan quadrisep lebih intensif.",
                "Fokus strengthening quad. Pertahankan ROM. Cryotherapy post sesi.");
        rm3.setSkalaNyeriVAS(6); rm3.setTekananDarah("120/80"); rm3.setNadiPerMenit(80);
        rekamMedisMap.put(rm3.getId(), rm3);

        // --- PROGRAM LATIHAN ---
        ProgramLatihan prog1 = new ProgramLatihan("PL001","Core Stabilization",
                "Low Back Pain","Program penguatan otot inti untuk punggung bawah",30,"Sedang","🧘");
        prog1.tambahLatihan("Cat-Cow Stretch — 3×10 repetisi");
        prog1.tambahLatihan("Dead Bug — 3×8 repetisi");
        prog1.tambahLatihan("Bird Dog — 3×10/sisi");
        prog1.tambahLatihan("Plank — 3×30 detik");
        prog1.tambahLatihan("Glute Bridge — 3×15 repetisi");
        prog1.setTujuan("Memperkuat otot inti, mengurangi nyeri punggung bawah");
        programMap.put(prog1.getId(), prog1);

        ProgramLatihan prog2 = new ProgramLatihan("PL002","Knee Rehab Protocol",
                "Rehabilitasi Lutut","Protokol rehabilitasi pasca operasi ACL/meniskus",45,"Tinggi","🦵");
        prog2.tambahLatihan("Quad Sets — 3×15 repetisi");
        prog2.tambahLatihan("Straight Leg Raise — 4×10");
        prog2.tambahLatihan("Wall Sit — 3×30 detik");
        prog2.tambahLatihan("Terminal Knee Extension — 3×15");
        prog2.tambahLatihan("Mini Squat — 3×12 repetisi");
        prog2.tambahLatihan("Step-Up Exercise — 3×10/kaki");
        prog2.setTujuan("Memulihkan kekuatan dan ROM lutut pasca operasi");
        programMap.put(prog2.getId(), prog2);

        ProgramLatihan prog3 = new ProgramLatihan("PL003","Stroke Rehab Program",
                "Neurologi","Program rehabilitasi pasca stroke untuk pemulihan motorik",60,"Rendah","🧠");
        prog3.tambahLatihan("ROM Aktif-Asistif seluruh anggota gerak");
        prog3.tambahLatihan("Latihan Propriosepsi");
        prog3.tambahLatihan("Gait Training dengan parallel bar");
        prog3.tambahLatihan("ADL Retraining (makan, berpakaian)");
        prog3.tambahLatihan("Balance Training duduk-berdiri");
        prog3.setTujuan("Memulihkan fungsi motorik dan kemandirian ADL");
        programMap.put(prog3.getId(), prog3);

        ProgramLatihan prog4 = new ProgramLatihan("PL004","Neck Pain Relief",
                "Neck Pain","Program untuk mengurangi nyeri leher dan meningkatkan mobilitas",30,"Rendah","🤸");
        prog4.tambahLatihan("Cervical Retraction — 3×10");
        prog4.tambahLatihan("Chin Tuck — 3×10 tahan 5 detik");
        prog4.tambahLatihan("Shoulder Rolls — 2×15 putaran");
        prog4.tambahLatihan("Upper Trap Stretch — 3×30 detik/sisi");
        prog4.tambahLatihan("Deep Neck Flexor Activation — 3×10");
        prog4.setTujuan("Mengurangi nyeri leher dan memperbaiki postur kepala");
        programMap.put(prog4.getId(), prog4);

        ProgramLatihan prog5 = new ProgramLatihan("PL005","Sports Injury Rehab",
                "Sports","Program pemulihan cedera olahraga",45,"Tinggi","⚽");
        prog5.tambahLatihan("RICE Protocol (Rest, Ice, Compression, Elevation)");
        prog5.tambahLatihan("Ankle/Knee ROM Exercise");
        prog5.tambahLatihan("Proprioception Board Training");
        prog5.tambahLatihan("Agility Ladder Drill");
        prog5.tambahLatihan("Functional Movement Screen");
        prog5.setTujuan("Pemulihan cedera dan kembali ke performa olahraga optimal");
        programMap.put(prog5.getId(), prog5);

        // --- TAGIHAN ---
        Tagihan tg1 = new Tagihan("INV-2026-0089","P001","S001","Fisioterapi Sesi ke-5",450000);
        tagihanMap.put(tg1.getId(), tg1);

        Tagihan tg2 = new Tagihan("INV-2026-0088","P002","S002","Fisioterapi Sesi ke-3",600000);
        tg2.bayar("Transfer BCA");
        tagihanMap.put(tg2.getId(), tg2);

        Tagihan tg3 = new Tagihan("INV-2026-0087","P003","S003","Stroke Rehab Sesi ke-8",750000);
        tg3.bayar("Cash");
        tagihanMap.put(tg3.getId(), tg3);

        Tagihan tg4 = new Tagihan("INV-2026-0085","P002","S006","Paket 5 Sesi Fisioterapi",2000000);
        tg4.setTanggalJatuhTempo(today.minusDays(1));
        tg4.setStatus(Tagihan.StatusTagihan.JATUH_TEMPO);
        tagihanMap.put(tg4.getId(), tg4);

        Tagihan tg5 = new Tagihan("INV-2026-0083","P005","S005","Konsultasi + Sesi ke-1",350000);
        tagihanMap.put(tg5.getId(), tg5);

        // --- NOTIFIKASI ---
        buatNotif("N001","P001","Sesi Besok Diingatkan",
                "Sesi Low Back Pain Anda besok pukul 08:30 bersama Dr. Rina.",
                "⏰", Notifikasi.TipeNotifikasi.PENGINGAT_SESI, 60);
        buatNotif("N002","P001","Tagihan Belum Dibayar",
                "Tagihan INV-2026-0089 sebesar Rp 450.000 menunggu pembayaran.",
                "💳", Notifikasi.TipeNotifikasi.TAGIHAN, 120);
        buatNotif("N003","P001","Sesi Selesai",
                "Sesi Low Back Pain Sesi ke-5 bersama Dr. Rina telah selesai. Catatan SOAP tersedia.",
                "✅", Notifikasi.TipeNotifikasi.SUKSES, 180);
        buatNotif("N004","P002","Pembayaran Diterima",
                "Pembayaran INV-2026-0088 sebesar Rp 600.000 telah dikonfirmasi.",
                "✅", Notifikasi.TipeNotifikasi.SUKSES, 300);
        buatNotif("N005","P003","Jadwal Sesi Terbaru",
                "Sesi Stroke Rehab ke-9 dijadwalkan pada tanggal " + today.plusDays(2) + " pukul 11:00.",
                "📅", Notifikasi.TipeNotifikasi.INFO, 400);
        buatNotif("N006","ALL","Pengumuman Klinik",
                "PhysioHub akan tutup pada Minggu, 15 Juni 2026 untuk perawatan rutin.",
                "📢", Notifikasi.TipeNotifikasi.INFO, 1440);
    }

    private void buatSesi(String id, String pasienId, String terapisId, LocalDate tgl,
                          LocalTime waktu, int durasi, String ruang, String program,
                          int sesiKe, Sesi.StatusSesi status) {
        Sesi s = new Sesi(id, pasienId, terapisId, tgl, waktu, durasi, ruang, program, sesiKe);
        s.setStatus(status);
        sesiMap.put(id, s);
    }

    private void buatNotif(String id, String userId, String judul, String pesan,
                           String emoji, Notifikasi.TipeNotifikasi tipe, long menitLalu) {
        Notifikasi n = new Notifikasi(id, userId, judul, pesan, emoji, tipe);
        n.setWaktu(LocalDateTime.now().minusMinutes(menitLalu));
        notifikasiMap.put(id, n);
    }

    // ===== CRUD METHODS =====

    // USER
    public void addUser(User u) { users.put(u.getId(), u); }
    public User getUserById(String id) { return users.get(id); }
    public User login(String email, String password) {
        return users.values().stream()
            .filter(u -> u.getEmail().equals(email) && u.verifyPassword(password))
            .findFirst().orElse(null);
    }
    public List<Pasien> getAllPasien() {
        return users.values().stream()
            .filter(u -> u instanceof Pasien).map(u -> (Pasien) u)
            .collect(Collectors.toList());
    }
    public List<Terapis> getAllTerapis() {
        return users.values().stream()
            .filter(u -> u instanceof Terapis).map(u -> (Terapis) u)
            .collect(Collectors.toList());
    }
    public Terapis getTerapisById(String id) {
        User u = users.get(id);
        return (u instanceof Terapis) ? (Terapis) u : null;
    }
    public Pasien getPasienById(String id) {
        User u = users.get(id);
        return (u instanceof Pasien) ? (Pasien) u : null;
    }

    // SESI
    public void addSesi(Sesi s) { sesiMap.put(s.getId(), s); }
    public Sesi getSesiById(String id) { return sesiMap.get(id); }
    public List<Sesi> getAllSesi() { return new ArrayList<>(sesiMap.values()); }
    public List<Sesi> getSesiByPasien(String pasienId) {
        return sesiMap.values().stream()
            .filter(s -> s.getPasienId().equals(pasienId))
            .sorted(Comparator.comparing(Sesi::getTanggal).thenComparing(Sesi::getWaktuMulai))
            .collect(Collectors.toList());
    }
    public List<Sesi> getSesiByTerapis(String terapisId) {
        return sesiMap.values().stream()
            .filter(s -> s.getTerapisId().equals(terapisId))
            .sorted(Comparator.comparing(Sesi::getTanggal).thenComparing(Sesi::getWaktuMulai))
            .collect(Collectors.toList());
    }
    public List<Sesi> getSesiHariIni() {
        LocalDate today = LocalDate.now();
        return sesiMap.values().stream()
            .filter(s -> s.getTanggal().equals(today))
            .sorted(Comparator.comparing(Sesi::getWaktuMulai))
            .collect(Collectors.toList());
    }

    // TAGIHAN
    public void addTagihan(Tagihan t) { tagihanMap.put(t.getId(), t); }
    public Tagihan getTagihanById(String id) { return tagihanMap.get(id); }
    public List<Tagihan> getAllTagihan() { return new ArrayList<>(tagihanMap.values()); }
    public List<Tagihan> getTagihanByPasien(String pasienId) {
        return tagihanMap.values().stream()
            .filter(t -> t.getPasienId().equals(pasienId))
            .collect(Collectors.toList());
    }

    // REKAM MEDIS
    public void addRekamMedis(RekamMedis rm) { rekamMedisMap.put(rm.getId(), rm); }
    public List<RekamMedis> getRekamMedisByPasien(String pasienId) {
        return rekamMedisMap.values().stream()
            .filter(rm -> rm.getPasienId().equals(pasienId))
            .sorted(Comparator.comparing(RekamMedis::getTanggal).reversed())
            .collect(Collectors.toList());
    }

    // PROGRAM LATIHAN
    public List<ProgramLatihan> getAllProgram() { return new ArrayList<>(programMap.values()); }
    public ProgramLatihan getProgramById(String id) { return programMap.get(id); }

    // NOTIFIKASI
    public void addNotifikasi(Notifikasi n) { notifikasiMap.put(n.getId(), n); }
    public List<Notifikasi> getNotifikasiByUser(String userId) {
        return notifikasiMap.values().stream()
            .filter(n -> n.getUserId().equals(userId) || n.getUserId().equals("ALL"))
            .sorted(Comparator.comparing(Notifikasi::getWaktu).reversed())
            .collect(Collectors.toList());
    }
    public long countUnreadNotifikasi(String userId) {
        return notifikasiMap.values().stream()
            .filter(n -> (n.getUserId().equals(userId) || n.getUserId().equals("ALL")) && !n.isSudahDibaca())
            .count();
    }
    public void tandaiSemuaDibaca(String userId) {
        notifikasiMap.values().stream()
            .filter(n -> n.getUserId().equals(userId) || n.getUserId().equals("ALL"))
            .forEach(Notifikasi::tandaiDibaca);
    }

    // STATISTIK
    public int getTotalPasienAktif() {
        return (int) getAllPasien().stream()
            .filter(p -> p.getStatusPasien() == Pasien.StatusPasien.AKTIF).count();
    }
    public int getTotalSesiHariIni() { return getSesiHariIni().size(); }
    public double getTotalPendapatanBulanIni() {
        return tagihanMap.values().stream()
            .filter(t -> t.getStatus() == Tagihan.StatusTagihan.LUNAS)
            .mapToDouble(Tagihan::getJumlah).sum();
    }
    public long getTagihanMenunggu() {
        return tagihanMap.values().stream()
            .filter(t -> t.getStatus() == Tagihan.StatusTagihan.MENUNGGU ||
                         t.getStatus() == Tagihan.StatusTagihan.JATUH_TEMPO).count();
    }
}
