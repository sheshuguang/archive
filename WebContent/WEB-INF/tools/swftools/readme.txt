version 2010-02-06-1900
pdf2swf.exe test.pdf -o test.swf -T 9 -f

Usage: pdf2swf.exe [-options] file.pdf -o file.swf

-h , --help                    Print short help message and exit
-V , --version                 Print version info and exit
-o , --output file.swf         Direct output to file.swf. If file.swf contains '%' (file%.swf), then each page goes to a seperate file.
-p , --pages range             Convert only pages in range with range e.g. 1-20 or 1,4,6,9-11 or
-P , --password password       Use password for deciphering the pdf.
-v , --verbose                 Be verbose. Use more than one -v for greater effect.
-z , --zlib                    Use Flash 6 (MX) zlib compression.
-i , --ignore                  Allows pdf2swf to change the draw order of the pdf. This may make the generated
-j , --jpegquality quality     Set quality of embedded jpeg pictures to quality. 0 is worst (small), 100 is best (big). (default:85)
-s , --set param=value         Set a SWF encoder specific parameter.  See pdf2swf -s help for more information.
-w , --samewindow              When converting pdf hyperlinks, don't make the links open a new window.
-t , --stop                    Insert a stop() command in each page.
-T , --flashversion num        Set Flash Version in the SWF header to num.
-F , --fontdir directory       Add directory to the font search path.
-b , --defaultviewer           Link a standard viewer to the swf file.
-l , --defaultloader           Link a standard preloader to the swf file which will be displayed while the main swf is loading.
-B , --viewer filename         Link viewer filename to the swf file.
-L , --preloader filename      Link preloader filename to the swf file.
-q , --quiet                   Suppress normal messages.  Use -qq to suppress warnings, also.
-S , --shapes                  Don't use SWF Fonts, but store everything as shape.
-f , --fonts                   Store full fonts in SWF. (Don't reduce to used characters).
-G , --flatten                 Remove as many clip layers from file as possible.
-I , --info                    Don't do actual conversion, just display a list of all pages in the PDF.
-Q , --maxtime n               Abort conversion after n seconds. Only available on Unix.


as3compile
A standalone ActionScript 3.0 compiler. 
font2swf
Converts font files (TTF, Type1) to SWF. 
gif2swf
Converts GIFs to SWF. Also able to handle animated gifs. 
jpeg2swf
Takes one or more JPEG pictures and generates a SWF slideshow from them. Supports motion estimation compression (h.263) for better compression of video sequences. 
swfcombine
A multi-function tool for inserting SWFs into Wrapper SWFs, contatenating SWFs, stacking SWFs or for basic parameter manipulation (e.g. changing size). 
pdf2swf
A PDF to SWF Converter. Generates one frame per page. Enables fully formatted text, ::including tables and formulas, inside a Flash Movie. It's based on the xpdf PDF parser from ::Derek B. Noonburg. 
png2swf
Like JPEG2SWF, only for PNGs. 
swfbbox
Allows to read out, optimize and readjust SWF bounding boxes. 
swfc
A tool for creating SWF files from simple script files. Includes support for both ActionScript 2.0 as well as ActionScript 3.0. 
swfdump
Prints out various informations about SWFs, like contained images/fonts/sounds, disassembly of contained code as well as cross-reference and bounding box data. 
swfextract
Allows to extract Movieclips, Sounds, Images etc. from SWF files. 
swfstrings
Scans SWFs for text data. 
swfrender
Creates bitmaps from swf files created with pdf2swf, jpeg2swf or png2swf. 
avi2swf
Converts AVI animation files to SWF (now deprecated, use mencoder or ffmpeg for this). 
wav2swf
Converts WAV audio files to SWFs.