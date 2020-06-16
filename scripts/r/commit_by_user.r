data <- read.table("D:/Backup/eclipse-workspace/PACOTE/data/log/jhotdrawlog_list.data", header=TRUE);

users <- sort(unique(data$user));

columns <- c("revision", "classes");
	result <- matrix(nrow=length(users), ncol=length(columns), dimnames=list(users, columns));
	lastVersion <- "";
		
	for (user_ in users)
	{
		userData <- subset(data, user == user_);
		
		result[user_, "revision"] <- length (unique(userData$revision));
		result[user_, "classes"] <- nrow(userData);
		
	}
	
	
	
	p <- c(4, 13, 8, 13, 13, 21, 21, 21, 21, 21, 24, 24, 24, 25, 25, 25, 29, 29, 30, 30, 59, 59, 59, 60);

c <- c(102, 173, 187, 265, 265, 401, 401, 406, 407, 407, 523, 524, 553, 576, 576, 576, 752, 769, 870, 873, 1090, 1093, 1094, 1116);


	
	res2 <-cor(p,c,  method = "spearman")
	
	                revision classes
birdscurrybeer         2       4
cfm1                   2      88
dnoyeb                90     916
gesworthy              1       6
jeckel                13     308
mrfloppy              62    4448
mtnygard               5     452
pleumann               7      19
pmorch                11      26
rawcoder             990   16284
ricardo_padilha       35     194