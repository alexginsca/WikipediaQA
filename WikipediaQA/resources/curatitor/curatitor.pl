#!/usr/local/bin/perl
# asta o sa ma ajute sa curat wiki romanesc

sub fisiere_de_curatat
{
	my @corpus =();

	opendir( CWD, 'de_prelucrat/') or die "Nu pot ajunge la fisierele de input: $!";
	while ( my $file = readdir( CWD ))
	{
		if ( $file =~ /^x/ ) {	push @corpus, $file }
	}	
	closedir( CWD );
	return sort( @corpus );
}

my @file = &fisiere_de_curatat;



for (my $i=0; $i<$#file+1; $i++)
{
	print "fisierul : @file[$i] \n";
	open ( F, "< de_prelucrat/@file[$i]") or die "Nu gasesc fisiere de prelucrat in \"de_prelucrat\"";
	open (R, "> rez-@file[$i].txt");	
	
	my @data = <F>;
	for (my $j; $j<$#data+1; $j++)
	{
		$ceva = @data[$j];
		my $ok = 1;
		my $aux;
		while ($ok > 0)
		{
		
			$aux = length ($ceva);
					
		#elemente xml
			$ceva =~ s/.+<\/?namespaces>\n//;
			$ceva =~ s/.+<namespace key=.+\/>\n//;
			$ceva =~ s/.+<namespace key=.+>.+<\/namespace>\n//;
			$ceva =~ s/.+<generator>.+<\/generator>\n//;
			$ceva =~ s/.+<case>.+<\/case>\n//;
			$ceva =~ s/.+<timestamp>.+<\/timestamp>\n//;
			
			#scot id			
			$ceva =~ s/.+<id>\d+<\/id>\n//;
			
			$ceva =~ s/.+<minor.*\/>\n//;
			$ceva =~ s/.+<minor>.+<\/minor>\n//;
			$ceva =~ s/.+<username>.+<\/username>\n//;
			$ceva =~ s/.+<\/?contributor>\n//;
			$ceva =~ s/.+<comment>.+<\/comment>\n//;
			
			
		#sectiuni articol
			$ceva =~ s/preserve\">\{\{.+\}?\}?/preserve\">/g;
			$ceva =~ s/^==.+?==.*\n//;
			$ceva =~ s/^\{\{.+?\}\}\n//;
			
			#referinte
			$ceva =~ s/&lt;ref&gt;Vedeți.+?&lt;\/ref&gt;//g;
			#de discutat referintele
			$ceva =~ s/&lt;ref name=.+?&gt;.+?&lt;\/ref&gt;//g;
			$ceva =~ s/&lt;ref name=.+?\/&gt;//g;
			$ceva =~ s/&lt;ref name=.+?&gt;.*\n//;
			$ceva =~ s/&lt;\/ref&gt;//g;
			#$ceva =~ s/&lt;ref&gt;Vedeți.+?&lt;\/ref&gt;//g;
			$ceva =~ s/&lt;\/?ref&gt;/ /g;
			$ceva =~ s/^\* \[.+\]\n//;
			$ceva =~ s/^\* \[.+?\]\n//g;
			$ceva =~ s/&lt;references ?\/&gt;[ \t]*?\n?//;
						
			# referinte adrese
			$ceva =~ s/\[http\:\/\/.+?\]//g;
			$ceva =~ s/^\* \[http\:\/\/.+?\]\n//;
			
			# referinte - fisiere
			$ceva =~ s/^\[\[Imagine:.+?\]\]\n?//g;
			$ceva =~ s/^\[\[Image:.+?\]\]\n?//g;
			$ceva =~ s/^\[\[Fișier:.+?\]\]\n?//g;
			
			#bibliografie
			my @test = split(/\W+/,  $ceva);
			if (scalar(@test) < 20)
			{
				$ceva =~ s/^\* '?'?.+\n//;
			}
			$ceva =~ s/^\[\[[a-z\-]+:.+?\]\]\n?//;
			
			#citari
			$ceva =~ s/\{\{necesită citare\}\}//;
			
			#pronunții
			$ceva =~ s/\(pronunțat.+?\{\{AFI\|.+?\}\}.*?\)//g;
			
			
		#html
			#taguri
			$ceva =~ s/&lt;br\/&gt;//g;
			$ceva =~ s/&lt;\/?center\/?&gt;//g;
				#font
			$ceva =~ s/&lt;font.+?&lt;//g;
			$ceva =~ s/\/font&gt;\n?//g;
				#h3
			$ceva =~ s/&lt;h3.+?&gt;\n?//g;
			$ceva =~ s/&lt;\/h3&gt;//g;
				#small
			$ceva =~ s/&lt;\/?small&gt;//;
				#span
			$ceva =~ s/&lt;span.*?&gt;//g;
			$ceva =~ s/&lt;\/span&gt;\n?//g;
				#divuri
			$ceva =~ s/&lt;div.*?&gt;//g;
			$ceva =~ s/&lt;\/div&gt;\n?//g;
				#inputbox
			$ceva =~ s/&lt;\/?inputbox&gt;\n?//g;
					#resturi
			$ceva =~ s/^(type|preload|width|editintro)=.+?\n//;
			$ceva =~ s/^\|\-\n//;
			$ceva =~ s/^\|\{\{.+?\n//;
			
			#tabele
				#continut
			$ceva =~ s/^\| ([A-ZĂÎÂȘȚ]+)?[a-zăîâșț]*.+\n//;
				#inceput si sf
			$ceva =~ s/^\{\{.+\n//;
			$ceva =~ s/^\}\}\n//;
			
			$ceva =~ s/^\{\|[ |id].+;?\n//;
			$ceva =~ s/^\|\}\n?//g;
			$ceva =~ s/^\{\|\n?//g;
			$ceva =~ s/^\|[ -]*?[a-z]+?.+\n//;
				#style
			$ceva =~ s/\! style=.+?\|//g;
			
			#comentarii
			$ceva =~ s/&lt;!\-\-.+?\-\-&gt;\n?//;
			
			
		#categorii
			if ($ceva=~ m/\[\[Categorie:.+?\|.*?\]\]/)
			{
				$ceva =~ s/\[\[Categorie:(.+?)\|.*?\]\]\n/Categorie:$1\n/;
			}	
			
		#text
			#caractere
			$ceva =~ s/&amp;bull//g;
			$ceva =~ s/\'\'\'//g;
			$ceva =~ s/\'\'/\"/g;
			$ceva =~ s/&quot;/\"/g;
			$ceva =~ s/&lt;br ?\/?&gt;//g;
			$ceva =~ s/&amp;nbsp;//g;
			#leme // probleme la numele proprii
			$ceva =~ s/\[\[([^\|]+?)\]\]/$1/g;
			if ($ceva=~ m/\[\[.+?\|.+?\]\]/)
			{
				$ceva =~ s/\[\[.+?\|//;
				$ceva =~ s/\]\]//;							
			}
			
			
		#wiki-variabile
			$ceva =~ s/\{\{NUMBEROFARTICLES:R\}\}//g;
			$ceva =~ s/\[\{\{fullurl:\{\{FULLPAGENAME\}\}\|action=purge\}\} aici\]//g;
			$ceva =~ s/\{\{#expr: round \-3\}\}//g;
			$ceva =~ s/\{\{en icon\}\}//g;
	
			if ($aux == length ($ceva))
			{
				$ok --;
				if ($ok == 0)
				{
					print R $ceva;
				}
			}
		}
	}
	
	close R;
	close F;
}
