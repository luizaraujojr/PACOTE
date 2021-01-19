#boxplot for metrics
project<-"JHotDraw"

data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics", project, ".data", sep = ""), header=TRUE);

JHotDraw_Exclude <- c("5.4.2", "7.2.0");
JEdit_Exclude <- as.factor(c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2"));

`%notin%` <- Negate(`%in%`)

if (project=="JHotDraw"	){
	data <- subset(data, versions %notin% JHotDraw_Exclude)
}

if (project=="JEdit"	){
	data <- subset(data, versions %notin% JEdit_Exclude)
}



pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_CBO.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$cbo~data$versions, main="CBO", xlab='', ylab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_AFF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$aff~data$versions, main="Afferent Coupling", xlab='', ylab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_EFF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$eff~data$versions, main="Efferent Coupling", xlab='', ylab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_LCOM.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$lcom~data$versions, main="LCOM", xlab='', ylab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_MF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$mf~data$versions, main="Modularization Factor", xlab='', ylab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/data/graph/", project, "_CS.pdf", sep = ""), width=8,height=3)
par(mar=c(4,4,3,3))
boxplot(data$cs~data$versions, outline=FALSE, main="Cluster Score", xlab='', ylab='', las =2);
dev.off();

