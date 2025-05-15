-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 15 Bulan Mei 2025 pada 18.49
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_restoran`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbmenu`
--

CREATE TABLE `tbmenu` (
  `id_menu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbmenu`
--

INSERT INTO `tbmenu` (`id_menu`) VALUES
(1),
(5),
(7),
(8),
(9),
(11),
(12),
(15),
(17),
(18);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbmenuresto`
--

CREATE TABLE `tbmenuresto` (
  `id_menuresto` int(11) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `harga` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  `kategori` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbmenuresto`
--

INSERT INTO `tbmenuresto` (`id_menuresto`, `id_menu`, `nama`, `harga`, `status`, `kategori`) VALUES
(1, 1, 'Nasi Goreng', 10000, 'TERSEDIA', 'MAKANAN'),
(2, 5, 'Nasi Padang', 20000, 'TERSEDIA', 'MAKANAN'),
(4, 7, 'Indomie Goreng', 10000, 'TERSEDIA', 'MAKANAN'),
(5, 8, 'Nasi Ayam', 9000, 'TERSEDIA', 'MAKANAN'),
(6, 9, 'Sate Ayam Taichan', 18000, 'TERSEDIA', 'MAKANAN'),
(8, 11, 'Nasi Bungkus', 3000, 'TERSEDIA', 'MAKANAN'),
(9, 12, 'Nasi Teriyaki', 10000, 'TERSEDIA', 'MAKANAN'),
(12, 15, 'Soto Ayam', 52000, 'TERSEDIA', 'MAKANAN'),
(14, 17, 'Chicken Steak', 20000, 'TERSEDIA', 'MAKANAN'),
(15, 18, 'Indomie Kuah', 8000, 'HABIS', 'MAKANAN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbtransaksi`
--

CREATE TABLE `tbtransaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nama_user` varchar(45) NOT NULL,
  `nama_pelanggan` varchar(30) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbtransaksi`
--

INSERT INTO `tbtransaksi` (`id_transaksi`, `id_user`, `nama_user`, `nama_pelanggan`, `id_menu`, `tanggal`, `nama_menu`, `kategori`, `harga`, `jumlah_beli`, `total_bayar`) VALUES
(1, 1, 'Daniel', 'Vicko', 1, '2024-12-04', 'Nasi Goreng', 'MAKANAN', 10000, 3, 30000),
(3, 1, 'Admin', 'Ontel', 1, '2024-12-04', 'Nasi Goreng', 'MAKANAN', 10000, 1, 10000),
(12, 1, 'Admin', 'Evan', 5, '2024-12-05', 'Nasi Padang', 'MAKANAN', 20000, 3, 60000),
(17, 3, 'Andika', 'Yunus', 11, '2024-12-05', 'Nasi Bungkus', 'MAKANAN', 3000, 20, 60000),
(21, 1, 'Admin', 'Orin', 5, '2024-12-07', 'Nasi Padang', 'MAKANAN', 20000, 3, 60000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbuser`
--

CREATE TABLE `tbuser` (
  `id_user` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `nama_user` varchar(30) NOT NULL,
  `id_level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbuser`
--

INSERT INTO `tbuser` (`id_user`, `username`, `password`, `nama_user`, `id_level`) VALUES
(1, 'admin', 'admin1234', 'Admin', 1),
(3, 'owner', 'owner1234', 'Andika', 3),
(4, 'pelanggan', '', 'Pelanggan', 4),
(10, 'admin2', '123', 'Yunus', 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbmenu`
--
ALTER TABLE `tbmenu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indeks untuk tabel `tbmenuresto`
--
ALTER TABLE `tbmenuresto`
  ADD PRIMARY KEY (`id_menuresto`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indeks untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_masakan` (`id_menu`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `tbuser`
--
ALTER TABLE `tbuser`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tbmenu`
--
ALTER TABLE `tbmenu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT untuk tabel `tbmenuresto`
--
ALTER TABLE `tbmenuresto`
  MODIFY `id_menuresto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT untuk tabel `tbuser`
--
ALTER TABLE `tbuser`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbmenuresto`
--
ALTER TABLE `tbmenuresto`
  ADD CONSTRAINT `tbmenuresto_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `tbmenu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD CONSTRAINT `tbtransaksi_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `tbuser` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbtransaksi_ibfk_3` FOREIGN KEY (`id_menu`) REFERENCES `tbmenu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
