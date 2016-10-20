#makefile

all:
	@echo "# Compilation des classes"
	find -name "*.java" | xargs javac -d bin -Xlint:unchecked

launch:
	@echo "# Execution de la classe Main"
	java -cp bin Main

interface:
	@echo "# Execution de la classe Interface"
	java -cp bin Interface

clean:
	@echo "# Nettoyage du dossier bin"
	rm bin/*