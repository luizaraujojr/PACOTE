#boxplot for metrics
software<-"JHotDraw"

data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics", software, ".data", sep = ""), header=TRUE);

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_CBO.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$cbo~data$version, main="CBO", xlab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_AFF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$aff~data$version, main="Afferent Coupling", xlab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_EFF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$eff~data$version, main="Efferent Coupling", xlab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_LCOM.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$lcom~data$version, main="LCOM", xlab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_MF.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$mf~data$version, main="Modularization Factor", xlab='', las =2);
dev.off();

pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", software, "_CS.pdf", sep = ""), width=8,height=3)
par(mar=c(4,3,3,3))
boxplot(data$cs~data$version, outline=FALSE, main="Cluster Score", xlab='', las =2);
dev.off();

