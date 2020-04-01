

#data <- read.table("/Users/Marcio Barros/Documents/GitHub/sbse-ant-unirio/results/metrics/package_metrics.data", header=TRUE);

#versions <- c("1.1.0", "1.2.0", "1.3.0", "1.4.0", "1.5.0", "1.6.0", "1.7.0", "1.8.0", "1.8.2", "1.9.0");

data <- read.table("D:/Backup/eclipse-workspace/projetotese/results/package_metrics.data", header=TRUE);

versions <- sort(unique(data$version));

#data <- subset(data, version %in% versions);
#data$version <- factor(data$version);

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "MF", "EVM", "CS");
result <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	
	result[version_, "CBO"] <- mean(vdata$cbo);
	result[version_, "EFF"] <- mean(vdata$eff);
	result[version_, "AFF"] <- mean(vdata$aff);
	result[version_, "LCOM"] <- mean(vdata[complete.cases(vdata), ]$lcom);
	result[version_, "MQ"] <- sum(vdata$mf);	
	result[version_, "MF"] <- mean(vdata$mf);	
	result[version_, "EVM"] <- sum(vdata$cs);	
	result[version_, "CS"] <- mean(vdata$cs);
}

result

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingCBO.pdf", width=16, height=10)
#par(mfrow=c(6, 1))
par(mar=c(9,3,3,3))
boxplot(data$cbo~data$version, main="CBO", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingAFF.pdf", width=16, height=10)
par(mar=c(9,3,3,3))
boxplot(data$aff~data$version, main="Afferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingEFF.pdf", width=16, height=10)
par(mar=c(9,3,3,3))
boxplot(data$eff~data$version, main="Efferent Coupling", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingLCOM.pdf", width=16, height=10)
par(mar=c(9,3,3,3))
boxplot(data$lcom~data$version, main="LCOM", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingMF.pdf", width=16, height=10)
par(mar=c(9,3,3,3))
boxplot(data$mf~data$version, main="Modularization Factor", xlab='', las =2);
dev.off();

pdf("D:/Backup/eclipse-workspace/projetotese/results/couplingCS.pdf", width=16, height=10)
par(mar=c(9,3,3,3))
boxplot(data$cs~data$version, outline=FALSE, main="Cluster Score", xlab='', las =2);
dev.off();
