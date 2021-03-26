	library(igraph)
	library("lubridate")
	
	project <-"JHotDraw"
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/images/", project, "_graph.pdf", sep = ""), width=8,height=6)
	par(mfrow = c(3,5), cex=0.2)
	
	#Diretório da base de dados observados a ser lida
	dataDir <- paste("D:\\Backup\\eclipse-workspace\\PACOTE\\results\\graph\\", project, sep = "")
	larquivos<-list.files(dataDir,full.names=TRUE)
	 
	for (i in 1:length(larquivos)){
		m <-read.table (row.names=1, file=larquivos[i], header=FALSE, sep=";")
		m<-as.matrix(m)
		g<-graph.adjacency(m, mode="undirected", weighted=TRUE)
		l<-layout_in_circle(g)
		plot(g, layout=l, edge.color="red", vertex.color="black", vertex.label="", vertex.size=3)
		text(0,-1.2, strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][2], col="black", cex=5)
		date_version<-as.Date(substr(strsplit(strsplit(larquivos[i],'/')[[1]][2],'-')[[1]][3],0,8),format="%d%m%Y")
		text(0,-1.5,  paste(month(date_version), year(date_version),sep="/"), col="black", cex=5)
	 }
	 dev.off();