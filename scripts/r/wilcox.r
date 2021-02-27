
baseDirectory <- "D:/Backup/eclipse-workspace";
#baseDirectory <- "C:/Users/User/Desktop/Codigos";

project<-"JHotDraw";

data <- read.table(paste(baseDirectory, "/PACOTE/results/ODEMPackageCharacteristics", project, ".data", sep = ""), header=TRUE);

unique_versions <- unique(data$versions);

metrics<- c("CBO", "LCOM", "AFF", "EFF");

stats <- matrix(nrow=length(unique_versions), ncol=length(metrics), dimnames=list(unique_versions, metrics));

v1 <- "";
for (unique_versions_ in unique_versions)
{
	v2 <- unique_versions_;
	if (v1!=v2){	
		if (v1!=""){
			v1data <- subset(data, versions == v1);
			v2data <- subset(data, versions == v2);
			stats[v2, "CBO"] <- wilcox.test(v1data$cbo, v2data$cbo)$p.value;
			stats[v2, "AFF"] <- wilcox.test(v1data$aff, v2data$aff)$p.value;
			stats[v2, "EFF"] <- wilcox.test(v1data$eff, v2data$eff)$p.value;
			stats[v2, "LCOM"] <- wilcox.test(v1data$lcom, v2data$lcom)$p.value;
		}
		v1<-v2;
	}
}

write.csv(stats,file=paste(baseDirectory, "/PACOTE/data/table/", project, "_wilcox.csv", sep = ""))