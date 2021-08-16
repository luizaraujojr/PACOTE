#create a list of the files from your target directory
file_list <- list.files(path="D:/Backup/eclipse-workspace/PACOTE/data/Experiment/ILSOutput/ils1000", full.names=TRUE)

#initiate a blank data frame, each iteration of the loop will append the data from the given file to this variable
dataset <- data.frame()

#had to specify columns to get rid of the total column
for (i in 1:length(file_list)){
	data <- read.table(file_list[i], header=FALSE, sep=";");
  
  dataset <- rbind(dataset, data);
}

versions <- sort(unique(dataset$V1));


colnames <- c("meanMQ", "sdMQ");
result <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(dataset, V1 == version_);
	
	result[version_, "meanMQ"] <- round(mean(vdata$V4),10);
	result[version_, "sdMQ"] <- round(sd(vdata$V4),10);
}

result
