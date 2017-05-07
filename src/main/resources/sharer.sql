-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2017 at 02:10 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sharer`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(10) UNSIGNED NOT NULL,
  `content` varchar(500) NOT NULL,
  `created` datetime NOT NULL,
  `post_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `id` int(10) UNSIGNED NOT NULL,
  `post_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(10) UNSIGNED NOT NULL,
  `content` varchar(255) NOT NULL,
  `redirect` varchar(50) NOT NULL,
  `created` datetime NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  `sender_id` int(10) UNSIGNED NOT NULL,
  `receiver_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `id` int(10) UNSIGNED NOT NULL,
  `caption` varchar(1000) NOT NULL,
  `created` datetime NOT NULL,
  `type` varchar(5) NOT NULL COMMENT '"photo" or "video"',
  `file_id` varchar(50) NOT NULL,
  `url` varchar(100) NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`id`, `caption`, `created`, `type`, `file_id`, `url`, `user_id`) VALUES
(1, 'Feeling the love from around the world on CR7 Denim. It means a lot! Remember to sign up at CR7.com', '2017-05-07 07:31:35', 'image', '0B1-Ays8AP0R6LWxHb3FlRjgxa2c', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6LWxHb3FlRjgxa2c', 2),
(2, 'The flower show is coming, 4th to 21st May! Do you need any more reasons to stay in the best spot in Madeira?', '2017-05-07 07:33:59', 'image', '0B1-Ays8AP0R6VlY5M2Ryb09GZEU', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6VlY5M2Ryb09GZEU', 2),
(3, 'Vamossssssssss well done boys', '2017-05-07 07:38:39', 'image', '0B1-Ays8AP0R6bF9hUS1SMnY2SUU', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6bF9hUS1SMnY2SUU', 2),
(4, '...', '2017-05-07 07:40:07', 'image', '0B1-Ays8AP0R6amhQNnFUclE1OFU', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6amhQNnFUclE1OFU', 2),
(5, '3 mosqueteiros', '2017-05-07 07:41:15', 'image', '0B1-Ays8AP0R6cXB6VHAtRFFrZHM', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6cXB6VHAtRFFrZHM', 2),
(6, 'UCL', '2017-05-07 07:41:57', 'image', '0B1-Ays8AP0R6TDI2RTFSeDRDRHc', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6TDI2RTFSeDRDRHc', 2),
(7, 'Together', '2017-05-07 07:43:33', 'image', '0B1-Ays8AP0R6cnJrNnRiVTJENTQ', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6cnJrNnRiVTJENTQ', 2),
(8, '...', '2017-05-07 07:44:17', 'image', '0B1-Ays8AP0R6X1ZkeWZJek9qaTA', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6X1ZkeWZJek9qaTA', 2),
(9, '7', '2017-05-07 07:45:04', 'image', '0B1-Ays8AP0R6aTRVT0tObUhvQXc', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6aTRVT0tObUhvQXc', 2),
(10, 'Madrid', '2017-05-07 07:46:06', 'image', '0B1-Ays8AP0R6eVlSUXQxeU5pMzA', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6eVlSUXQxeU5pMzA', 2),
(11, 'A new project is coming soon… stay tuned', '2017-05-07 07:47:40', 'image', '0B1-Ays8AP0R6Vnh3N3MtR3lQeVk', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6Vnh3N3MtR3lQeVk', 2),
(12, 'So proud of my boy!!! hat trick', '2017-05-07 07:48:52', 'video', '0B1-Ays8AP0R6bk5kTkhSVFo2b28', 'https://drive.google.com/file/d/0B1-Ays8AP0R6bk5kTkhSVFo2b28/preview', 2),
(13, 'Felicidades chaval !!!! Vamos arriba', '2017-05-07 08:00:42', 'image', '0B1-Ays8AP0R6RnZseEY2dXhLaDg', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6RnZseEY2dXhLaDg', 3),
(14, 'Arroz con leche con @dulcedelechemardel buenisimo !!!!', '2017-05-07 08:01:24', 'image', '0B1-Ays8AP0R6LWdOYTFlZjZpYm8', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6LWdOYTFlZjZpYm8', 3),
(15, 'Llegaron mis nuevos botines', '2017-05-07 08:03:15', 'image', '0B1-Ays8AP0R6MjJxZWtFWllWbkk', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6MjJxZWtFWllWbkk', 3),
(16, ':/', '2017-05-07 08:03:58', 'image', '0B1-Ays8AP0R6UVc0eFlxVnNGOGc', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6UVc0eFlxVnNGOGc', 3),
(17, 'Terminando el año en la pile . feliz 2017 para todos !!!!', '2017-05-07 08:04:40', 'image', '0B1-Ays8AP0R6ZjQxQnNtUVBfbnM', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6ZjQxQnNtUVBfbnM', 3),
(18, 'Festejando el cumple de la vieji', '2017-05-07 08:05:21', 'image', '0B1-Ays8AP0R6UlFpMUE4eThzQjg', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6UlFpMUE4eThzQjg', 3),
(19, '...', '2017-05-07 08:06:01', 'image', '0B1-Ays8AP0R6VDZRc0xOdWc5NFk', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6VDZRc0xOdWc5NFk', 3),
(20, 'Comida con amigos ...', '2017-05-07 08:06:36', 'image', '0B1-Ays8AP0R6WERJeS13WEIxNVU', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6WERJeS13WEIxNVU', 3),
(21, 'Hello Hello Mateo', '2017-05-07 08:08:54', 'video', '0B1-Ays8AP0R6d3JGRkF6clBxM2M', 'https://drive.google.com/file/d/0B1-Ays8AP0R6d3JGRkF6clBxM2M/preview', 3),
(22, 'Feliz cumple coco !!!!', '2017-05-07 08:11:24', 'image', '0B1-Ays8AP0R6MWtJenlKTk5mY00', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6MWtJenlKTk5mY00', 3),
(23, '¡Arrancando el día con unos mates!! #buenosdias', '2017-05-07 08:12:07', 'image', '0B1-Ays8AP0R6M1ltVWxnTnRZbFE', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6M1ltVWxnTnRZbFE', 3);

-- --------------------------------------------------------

--
-- Table structure for table `relationship`
--

CREATE TABLE `relationship` (
  `follower` int(10) UNSIGNED NOT NULL,
  `followed` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MEMBER');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) UNSIGNED NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `facebook_id` varchar(16) DEFAULT NULL,
  `folder_id` varchar(50) DEFAULT NULL,
  `is_locked` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `username`, `email`, `password`, `avatar`, `facebook_id`, `folder_id`, `is_locked`) VALUES
(1, 'Nguyễn Tuấn Anh', 'yuen', 'inagsimiquel@yahoo.com.vn', '$2a$10$ax4Rd7J.ArXWNKIS4FXp7ehZuBY74koMpBmUpYh8jHRZhc.CenUyC', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6UHA5TkNLV0xRXzg', '1242524072527647', '0B1-Ays8AP0R6UEItNVpVSGQ4LU0', b'0'),
(2, 'Cristiano Ronaldo', 'cristiano', 'cristiano@gmail.com', '$2a$10$yIyBq6Z3boUTJVYrE7rHE.n9Irm8Yg.fs5.oDIhKtFR9BZk39qgG2', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6UHA5TkNLV0xRXzg', NULL, '0B1-Ays8AP0R6QmxtMUQ0cG83NFU', b'0'),
(3, 'Leo Messi', 'leomessi', 'leomessi@gmail.com', '$2a$10$VdWvHPAUlQvdoJkur7iR2ecHrCtD11z.RUp354z0pZjnVrYERily6', 'https://drive.google.com/uc?export=download&id=0B1-Ays8AP0R6cldIbDB5MmVvUGs', NULL, '0B1-Ays8AP0R6UmNaSHVfVGVUUDg', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `post_id` (`post_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `post_id` (`post_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender` (`sender_id`),
  ADD KEY `receiver` (`receiver_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `relationship`
--
ALTER TABLE `relationship`
  ADD PRIMARY KEY (`follower`,`followed`),
  ADD KEY `followed` (`followed`),
  ADD KEY `follower` (`follower`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `likes`
--
ALTER TABLE `likes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `relationship`
--
ALTER TABLE `relationship`
  ADD CONSTRAINT `relationship_ibfk_1` FOREIGN KEY (`follower`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `relationship_ibfk_2` FOREIGN KEY (`followed`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
