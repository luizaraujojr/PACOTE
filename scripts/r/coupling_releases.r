
data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics.data", header=TRUE);

versions <- sort(unique(data$version));

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "MF", "EVM", "CS");
result <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	
	result[version_, "CBO"] <- round(mean(vdata$cbo),2);
	result[version_, "EFF"] <- round(mean(vdata$eff),2);
	result[version_, "AFF"] <- round(mean(vdata$aff),2);
	result[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom),2);
	result[version_, "MQ"] <- round(sum(vdata$mf),2);	
	result[version_, "MF"] <- round(mean(vdata$mf),2);	
	result[version_, "EVM"] <- round(sum(vdata$cs),2);	
	result[version_, "CS"] <- round(mean(vdata$cs),2);
}

result

#boxplot for metrics

#-------JHotDraw
data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristicsJHotDraw.data", header=TRUE);

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCBOJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cbo~data$version, main="CBO", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingAFFJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$aff~data$version, main="Afferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingEFFJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$eff~data$version, main="Efferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingLCOMJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$lcom~data$version, main="LCOM", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingMFJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$mf~data$version, main="Modularization Factor", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCSJHotDraw.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cs~data$version, outline=FALSE, main="Cluster Score", xlab='', las =2);
dev.off();


#-----------JEdit

data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristicsJEdit.data", header=TRUE);

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCBOJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cbo~data$version, main="CBO", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingAFFJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$aff~data$version, main="Afferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingEFFJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$eff~data$version, main="Efferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingLCOMJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$lcom~data$version, main="LCOM", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingMFJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$mf~data$version, main="Modularization Factor", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCSJEdit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cs~data$version, outline=FALSE, main="Cluster Score", xlab='', las =2);
dev.off();




#-------------------------JUnit

data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristicsJUnit.data", header=TRUE);

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCBOJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cbo~data$version, main="CBO", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingAFFJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$aff~data$version, main="Afferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingEFFJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$eff~data$version, main="Efferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingLCOMJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$lcom~data$version, main="LCOM", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingMFJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$mf~data$version, main="Modularization Factor", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/pacote/results/couplingCSJUnit.pdf", width=8,height=5)
par(mar=c(9,3,3,3))
boxplot(data$cs~data$version, outline=FALSE, main="Cluster Score", xlab='', las =2);
dev.off();
