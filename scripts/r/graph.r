	library(igraph)
	library("lubridate")
	par(mfrow = c(4,4), cex=0.2)
	
	#Diretório da base de dados observados a ser lida
		dataDir <- 'D:\\Backup\\eclipse-workspace\\PACOTE\\results\\graph\\jhotdraw'
		 
		larquivos<-list.files(dataDir,full.names=TRUE)
	 
	for (i in 1:length(larquivos)){
		m <-read.table (row.names=1, file=larquivos[i], header=FALSE, sep=";")
		m<-as.matrix(m)
		g<-graph.adjacency(m, mode="undirected", weighted=TRUE)
		l<-layout_in_circle(g)
		plot(g, layout=l, edge.color="red", vertex.color="black", vertex.label="", vertex.size=2)
		text(0,-1.1, strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][2], col="black", cex=2)
		date_version<-as.Date(substr(strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][3],0,8),format="%d%m%Y")
		text(0,-1.4,  paste(month(date_version), year(date_version),sep="/"), col="black", cex=2)
	 }
	 
	 
	 
	#pdf('D:\\Backup\\eclipse-workspace\\PACOTE\\results\\graph\\jedit\\jedit_graph.pdf', width=16, height=10)
	library(igraph)
	library("lubridate")
	par(mfrow = c(8,5), cex=0.2)
	
	#Diretório da base de dados observados a ser lida
		dataDir <- 'D:\\Backup\\eclipse-workspace\\PACOTE\\results\\graph\\jedit'
		 
		larquivos<-list.files(dataDir,full.names=TRUE)
	 
	for (i in 1:length(larquivos)){
		m <-read.table (row.names=1, file=larquivos[i], header=FALSE, sep=";")
		m<-as.matrix(m)
		g<-graph.adjacency(m, mode="undirected", weighted=TRUE)
		l<-layout_in_circle(g)
		plot(g, layout=l, edge.color="red", vertex.color="black", vertex.label="", vertex.size=2)
		text(0,-1.1, strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][2], col="black", cex=2)
		date_version<-as.Date(substr(strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][3],0,8),format="%d%m%Y")
		text(0,-1.4,  paste(month(date_version), year(date_version),sep="/"), col="black", cex=2)
	 }
	 
	 dev.off();
	 
	 
	 	 
	library(igraph)
	library("lubridate")
	par(mfrow = c(5,5), cex=0.2)
	
	#Diretório da base de dados observados a ser lida
		dataDir <- 'D:\\Backup\\eclipse-workspace\\PACOTE\\results\\graph\\junit'

		larquivos<-list.files(dataDir,full.names=TRUE)
	 
	for (i in 1:length(larquivos)){
		m <-read.table (row.names=1, file=larquivos[i], header=FALSE, sep=";")
		m<-as.matrix(m)
		g<-graph.adjacency(m, mode="undirected", weighted=TRUE)
		l<-layout_in_circle(g)
		plot(g, layout=l, edge.color="red", vertex.color="black", vertex.label="", vertex.size=2)
		text(0,-1.1, strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][2], col="black", cex=2)
		date_version<-as.Date(substr(strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][3],0,8),format="%d%m%Y")
		text(0,-1.4,  paste(month(date_version), year(date_version),sep="/"), col="black", cex=2)
	 }
	 