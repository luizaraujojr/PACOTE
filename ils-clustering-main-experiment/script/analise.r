rm(list = ls());
library(tidyverse);

#
# Instances of interest
#
filenames <- tribble(
  ~filename,  ~instance, ~classes,
  "apache_lucene_core 741C", "lucene", 741,
  "eclipse_jgit 912C", "jgit", 912,
  "gae_plugin_core 140C", "gae", 140,
  "javaws 378C", "javaws", 378,
  "jedit-5.5.0", "jedit", 1366,
  "jhotdraw-7.6.0-09012011","jhotdraw", 1195,
  "Jung-graph 207C", "jung", 207,
  "junit-4.12.3-09112014","junit", 348,
  "notepad-full 299C", "notepad", 299,
  "udt-java 56C", "udt", 56,
  "xmldom 119C", "xmldom", 119,
  "y_base 558C", "y_base", 558,
  "ylayout 1162C", "ylayout", 1162
);


#
# Load all data
#
data <- NULL;

colspec <- cols(
  instance = col_character(),
  runtime = col_double(),
  evaluationsConsumed = col_double(),
  Fitness = col_double(),
  Solution = col_character(),
  bestFitness = col_double(),
  Time = col_double(),
  bestSolution = col_character()
);

for (filename_ in filenames$filename) {

  fullpath <- paste0("D://Backup//eclipse-workspace//PACOTE//ils-clustering-main-experiment//data//clustering//-2+2_20x_400eval//", filename_, ".odem");
  #fullpath <- paste0("D://Backup//eclipse-workspace//PACOTE//ils-clustering-main-experiment//data//clustering//-10+10_20x_400evals//", filename_, ".odem");


  instanceName <- filenames %>% 
    filter(filename == filename_) %>% 
    pull(instance) %>% 
    as.character();
  
  filedata <- read_delim(fullpath, delim=";", col_types=colspec) %>% 
    mutate(instance = instanceName) %>% 
    select(instance, round=runtime, evaluations=evaluationsConsumed, fitness=bestFitness, time=Time);

  if (is.null(data)) {
    data <- filedata;
  }
  else {
    data <- data %>% add_row(filedata);
  }
}


#
# Plot all time-based charts
#
ggplot(data, aes(evaluations, fitness, color=instance)) +
  facet_wrap(.~round) +
  geom_line();



#
# Plot all convergence boxplots
#
maxFitness <- data %>% 
  group_by(instance, round) %>% 
  summarize(maxfitness = max(fitness), .groups="keep") %>% 
  ungroup();

hitTop <- data %>% 
  inner_join(maxFitness, by=c("instance", "round")) %>% 
  filter(fitness == maxfitness) %>% 
  group_by(instance, round) %>% 
  summarize(evaluation = min(evaluations), maxfitness = max(maxfitness), .groups="keep");

maxTime <- data %>% 
  group_by(instance, round) %>% 
  summarize(time = max(time), .groups="keep") %>% 
  ungroup();

hitTop <- hitTop %>% 
  inner_join(maxTime, by=c("instance", "round")) %>% 
  inner_join(filenames, by="instance") %>% 
  arrange(classes, round) %>% 
  select(-filename);
  
#boxplot(hitTop$evaluation~hitTop$instance);





boxplot(hitTop$time~hitTop$instance);


#
# Correlation between claasses and maxfitness = -0.45 (the more classes, the harder to get fitness - average)
#
cor(hitTop$classes, hitTop$maxfitness, method="spearman")


#
# Correlation between claasses and evaluations = 0.25 (the more classes, the more evaluations needed - weak)
#
cor(hitTop$classes, hitTop$evaluation, method="spearman")
