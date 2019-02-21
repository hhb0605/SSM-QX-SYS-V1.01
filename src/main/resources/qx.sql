-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: qxsys
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_article`
--

DROP TABLE IF EXISTS `sys_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `title_pic` varchar(200) DEFAULT NULL COMMENT '封面图片',
  `author` varchar(100) NOT NULL COMMENT '作者',
  `content` mediumtext COMMENT '内容',
  `content_md` mediumtext COMMENT '内容-Markdown',
  `origin` varchar(100) DEFAULT NULL COMMENT '来源',
  `state` varchar(100) NOT NULL COMMENT '状态',
  `eye_count` bigint(20) DEFAULT '0' COMMENT '浏览量',
  `publish_time` timestamp NOT NULL DEFAULT '1970-01-31 16:00:01' COMMENT '发布时间',
  `edit_time` timestamp NOT NULL DEFAULT '1970-01-31 16:00:01' COMMENT '上次修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_article`
--

LOCK TABLES `sys_article` WRITE;
/*!40000 ALTER TABLE `sys_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_article_category`
--

DROP TABLE IF EXISTS `sys_article_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_article_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='文章&&分类关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_article_category`
--

LOCK TABLES `sys_article_category` WRITE;
/*!40000 ALTER TABLE `sys_article_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_article_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_article_tags`
--

DROP TABLE IF EXISTS `sys_article_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_article_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tags_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='文章&&标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_article_tags`
--

LOCK TABLES `sys_article_tags` WRITE;
/*!40000 ALTER TABLE `sys_article_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_article_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_category`
--

DROP TABLE IF EXISTS `sys_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_category`
--

LOCK TABLES `sys_category` WRITE;
/*!40000 ALTER TABLE `sys_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_comments`
--

DROP TABLE IF EXISTS `sys_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `p_id` bigint(20) DEFAULT '0' COMMENT '父级ID，给哪个留言进行回复',
  `c_id` bigint(20) DEFAULT '0' COMMENT '子级ID，给哪个留言下的回复进行评论',
  `article_title` varchar(200) DEFAULT NULL COMMENT '文章标题',
  `article_id` bigint(20) DEFAULT NULL COMMENT '文章ID',
  `author` varchar(200) DEFAULT NULL COMMENT '留言人',
  `author_id` varchar(200) DEFAULT NULL COMMENT '给谁留言',
  `email` varchar(100) DEFAULT NULL COMMENT '留言邮箱',
  `content` text COMMENT '留言内容',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  `url` varchar(200) DEFAULT NULL COMMENT '链接',
  `state` varchar(100) DEFAULT '正常' COMMENT '状态',
  `sort` bigint(20) DEFAULT '0' COMMENT '分类：0默认文章详情页，1友链页，2关于我页',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_comments`
--

LOCK TABLES `sys_comments` WRITE;
/*!40000 ALTER TABLE `sys_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_links`
--

DROP TABLE IF EXISTS `sys_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_links` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '连接名称',
  `url` varchar(200) DEFAULT NULL COMMENT '连接URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='友链表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_links`
--

LOCK TABLES `sys_links` WRITE;
/*!40000 ALTER TABLE `sys_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tags`
--

DROP TABLE IF EXISTS `sys_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tags`
--

LOCK TABLES `sys_tags` WRITE;
/*!40000 ALTER TABLE `sys_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `nickname` varchar(100) NOT NULL COMMENT '昵称',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(200) NOT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'tycoding','菜鸡一枚','b9fd429a10b73d2c0971db1d4ad975a9','9a924d8ae9800c229c892eeba7d413a6','233333@qq.com','http://img.api.tycoding.cn/avatar.jpg');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-21 19:59:47
