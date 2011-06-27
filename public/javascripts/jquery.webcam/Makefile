
SWFMILL     := swfmill
MTASC       := ./mtasc/mtasc 

MTASCSTDLIB := ./mtasc/std8

main:
	$(SWFMILL) simple src/jscam.xml jscam.swf
	$(MTASC) -v -frame 1 -swf jscam.swf -main jscam.as -version 8 -cp src -cp $(MTASCSTDLIB)

clean:
	rm -f jscam.swf

