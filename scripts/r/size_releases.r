#data <- read.table("/Users/Marcio/Documents/GitHub/Pesquisa/SBSE/sbse-ant-unirio/size_metrics.data", header=TRUE);


	data <- read.table("D:/Backup/eclipse-workspace/projetotese/results/jar_file_metrics.data", header=TRUE);
	unique_versions <- unique(data$versions);
	colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths", "NAC");
	result <- matrix(nrow=length(unique_versions), ncol=length(colnames), dimnames=list(unique_versions, colnames));

	for (version_ in unique_versions)
	{
		vdata <- subset(data, versions == version_);
		classes <- split(vdata, vdata$package);
		
		result[version_, "Packages"] <- length(unique(vdata$packages));
		result[version_, "Classes"] <- length(vdata$classes);
		result[version_, "Attrs"] <- sum(vdata$attrs);
		result[version_, "Meths"] <- sum(vdata$meths);
		result[version_, "PMeths"] <- sum(vdata$pmeth);	
		result[version_, "NAC"] <- sd(unlist(lapply(classes, nrow)));
	}
	result



# box-plot for the number of classes per package
	data_classespackages <- read.table("D:/Backup/eclipse-workspace/projetotese/results/jar_file_metrics.data", header=TRUE);
	unique_versions <- unique(data_classespackages$versions);
	uniqueVersionPackage <- unique(data_classespackages[,c("versions","packages")])
	colnames <- c("version", "package", "ClassesPackages");
	result1 <- matrix(nrow=nrow(uniqueVersionPackage),  ncol=length(colnames))
	colnames(result1) <- c("version", "package", "ClassesPackages");

	i <-1
	for (version_ in unique_versions)
	{
		vdata <- subset(data_classespackages, versions == version_);
		unique_packages <- unique(vdata$packages);
		for (packages_ in unique_packages)
		{
			zdata <- subset(vdata, packages == packages_);
			result1[i, 1] <- substring (version_,1,nchar(version_)-4); 
			result1[i, 2] <- packages_; 
			result1[i, 3] <- length(zdata$classes); 
			
			i<- i+1
		}
	}
	
	par(mar=c(7,3,3,3))
	
	boxplot(as.numeric(result1[,3]) ~ result1[,1], data = result1, main="Class/Package", xlab='', ylab='Number of Classes/Package', las=2)
	

# box-plot for the number of attributes per class
par(mar=c(9,3,3,3))
boxplot(data[,"attrs"] ~data[,"versions"], data= data, main="Attribute/Class", xlab='', las=2)

	# box-plot for the number of methods per class
	par(mar=c(9,3,3,3))
	boxplot(data[,"meths"] ~data[,"versions"], data= data, main="Methods/Class", xlab='', las=2)

par(mar=c(9,3,3,3))
boxplot(data[,"pmeths"] ~data[,"versions"], data= data, main="Public Methods/Class", xlab='', las=2)


# box-plot for the number of dependencies per class
data1 <- read.table("D:/Backup/eclipse-workspace/projetotese/results/projects_coupling_metrics.data", header=TRUE);
par(mar=c(15,3,3,3))
boxplot(data1[,"dependencyCount"] ~data1[,"versao"], data= data1, main="Dependency/Class", xlab='', las=2)









# Publishes results from the normality test (95%)
publishNormal <- function(pvalue, name) {
	if (pvalue < 0.05) {
		cat("Existem evidências de que a distribuição de ", name, " não é normal (", pvalue, ").\n", sep="");
	} else {
		cat("Nao e possivel negar que a distribuição de ", name, " é normal (", pvalue, ").\n", sep="");
	}
}

# Normality tests
p <- c(4, 13, 8, 13, 13, 21, 21, 21, 21, 21, 24, 24, 24, 25, 25, 25, 29, 29, 30, 30, 59, 59, 59, 60);
publishNormal(shapiro.test(p)$p.value, "numero de pacotes");

c <- c(102, 173, 187, 265, 265, 401, 401, 406, 407, 407, 523, 524, 553, 576, 576, 576, 752, 769, 870, 873, 1090, 1093, 1094, 1116);
publishNormal(shapiro.test(c)$p.value, "numero de classes");

a <- c(141, 249, 294, 410, 410, 584, 584, 591, 593, 597, 797, 797, 843, 880, 880, 880, 1162, 1191, 1361, 1363, 1719, 1723, 1725, 1875);
publishNormal(shapiro.test(a)$p.value, "numero de atributos");

m <- c(816, 1521, 1548, 2193, 2202, 3379, 3389, 3457, 3465, 3465, 4651, 4679, 4884, 5124, 5126, 5129, 6581, 6793, 7725, 7770, 9879, 9922, 9934, 10103);
publishNormal(shapiro.test(m)$p.value, "numero de metodos");

pm <- c(633, 1186, 1223, 1729, 1735, 2639, 2644, 2703, 2708, 2708, 3639, 3665, 3817, 3971, 3973, 3973, 4978, 5088, 5686, 5720, 7248, 7275, 7275, 7475);
publishNormal(shapiro.test(pm)$p.value, "numero de metodos publicos");

d <- c(362, 803, 855, 1228, 1230, 1893, 1894, 1942, 1947, 1947, 2557, 2562, 2445, 2566, 2566, 2568, 3505, 3583, 4127, 4138, 5329, 5351, 5354, 5522);
publishNormal(shapiro.test(d)$p.value, "numero de dependencias");

nac <- c(8.305131, 11.919944, 13.014406, 16.849349, 16.849349, 20.834375, 20.834375, 21.118125, 21.231429, 21.231429, 26.048523, 26.132067, 27.700993, 29.272665, 29.272665, 29.272665, 34.451254, 34.936447, 38.476841, 38.567758, 38.27966, 38.35064, 38.346258, 37.336256);
publishNormal(shapiro.test(nac)$p.value, "elegancia de classes");

