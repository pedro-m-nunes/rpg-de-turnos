// Pedro Müller Nunes, início em 11/11/2020, versão 1.0 em 20/03/2021 (refazer escolha de ataques do inimigo [v1.1])
import java.util.Random;

public class pokemon {
	static int hp = 100, hpE = 100, turno = 1;
	static boolean turnoValido = true, jogando = false;
	static String hpHud = "OOOOOOOOOO", hpEHud = "OOOOOOOOOO", hpEMostrar = "???";
	
	static Random cA = new Random();
	static Random sortDMG = new Random(); //random.nextInt(max - min + 1) + min
	
	// Metodo _ataque
	final static String[] nomeAtaque = {"Soco", "Investida", "Machadada", "Flechada"};
	
	// Metodo _ataqueInimigo
	final static String[] nomeAtaqueE = {"Investida", "Bicada", "Arranhada", "Rasante"};
	static Random ataqueE = new Random();
	
	// Itens
	static byte itemQ[] = {1, 1, 2};
	static boolean lupaUsada;
	
	// Metodo _usarDardo e _verificaDardo
	static int envAteTurno;
	static String efeitoE = "";
	
	public static void main(String[] args) {
		char comando, menuOp, quitOp;
		boolean menu = true, quitmenu = true;
		
		System.out.print("\n   [!] Este jogo foi feito para ser jogado na janela do CMD, com o tamanho padrao e nao em tela cheia. \n   Digite o numero e pressione 'ENTER' para escolher a opcao.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		do {
			System.out.println("+-------------------------------{ JOGO RPG DE TURNOS }-------------------------------+");
			System.out.println("|                                                                                    |");
			System.out.println("|     MENU                                                                           |");
			System.out.println("|                                                                                    |");
			System.out.println("|  1. Jogar                                                                          |");
			System.out.println("|  2. Como jogar                                                                     |");
			System.out.println("|  3. Guia (Ataques e Itens)                                                         |");
			System.out.println("|  4. Sair                                                                           |");
			System.out.println("|                                                                                    |");
			System.out.println("|                                                                                    |");
			System.out.println("|                           Pedro Müller Nunes, 2020-2021                           |");
			System.out.println("+------------------------------------------------------------------------------------+");
			
			System.out.print("   > ");
			
			try {
				menuOp = System.console().readLine().charAt(0);
			} catch(Exception StringIndexOutOfBoundsException) {
				menuOp = ' ';
			}
			
			switch(menuOp) {
				case '1': System.out.print("   Jogo iniciado.\n"); _resetValues(); jogando = true; break;
				case '2': System.out.println("   Como jogar:\n     Digite os comandos (numeros para atacar, letras para usar itens) e pressione 'ENTER' para realizar a acao.\n     Nos menus, selecione as opcoes usando numeros.\n     Para voltar ao menu principal durante o jogo, digite 'X'.\n\n\n\n\n\n\n\n\n\n\n\n\n"); break;
				
				case '3': System.out.println("   Guia: \n     <Ataques>\n       1 - 'Soco': causa 5 a 8 de dano, com 99% de chance de acerto.\n       2 - 'Investida': causa 10 de dano e pode sofrer ate 2, com 89% de chance de acerto.\n" + 
				"       3 - 'Machadada': causa 13 de dano e pode sofrer ate 4, com 73% de chance de acerto.\n       4 - 'Flechada': causa 20 de dano, com 13% de chance de acerto." + 
				"\n\n     <Itens>\n       Q - 'Remedio': cura 10 HP e desvia do ataque inimigo no turno usado.\n       W - 'Dardo': envenena o inimigo por 3 turnos, causando de 2 a 3 de dano por turno.\n       E - 'Lupa': revela o HP inimigo por 1 turno.\n\n\n\n\n\n"); break;
				
				case '4': System.out.println("   Programa finalizado."); System.exit(0); break;
				default: System.out.println("   Opcao invalida.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); break;
			}
			
			while(jogando) {
				System.out.print("\n");
				
				// Hud (26 linhas, 119 caracteres por linha)
				System.out.printf("+-----{ Turno %02d }----------------------------------------------------------------------------------------------------+\n", turno);
				System.out.printf("|                                                                                                                     |\n");
				System.out.printf("|                                                                                        %5s HP %s <%3s>    |\n", efeitoE, hpEHud, hpEMostrar);
				System.out.printf("|                                                                                                                     |\n");
				System.out.printf("|                                                                                            |\\       /|              |\n");
				System.out.printf("|                                                                                            | \\  _  / |              |\n");
				System.out.printf("|                                                                                             \\ \\/%%\\/ /               |\n");
				System.out.printf("|                                                                                              | \\v/ |                |\n");
				System.out.printf("|                 _                                                                             \\   /                 |\n");
				System.out.printf("|                /o\\  _                                                                         /\\_/\\                 |\n");
				System.out.printf("|                \\_/  +)                                                                       d     b                |\n");
				System.out.printf("|                /|\\__|                                                                                               |\n");
				System.out.printf("|               / |   |                                                                                               |\n");
				System.out.printf("|                / \\                                                                                                  |\n");
				System.out.printf("|               /   \\                                                                                                 |\n");
				System.out.printf("|                                                                                                                     |\n");
				System.out.printf("|           HP %s <%3d>                                                                                       |\n", hpHud, hp);
				System.out.printf("|                                                                                                                     |\n");
				System.out.printf("|                                                                                                                     |\n");
				System.out.printf("+-----{ Ataques }------------------------------------------------------------+-----{ Itens }--------------------------+\n");
				System.out.printf("|                                                                            |                                        |\n");
				System.out.printf("|   1. %-23s <5-8>    2. %-23s < 10> -   |   Q. Remedio <+10 HP>            [%d]   |\n", nomeAtaque[0], nomeAtaque[1], itemQ[0]);
				System.out.printf("|                                                                            |   W. Dardo   <Envenenamento>     [%d]   |\n", itemQ[1]);
				System.out.printf("|   3. %-23s < 13> -  4. %-23s < 20> %%   |   E. Lupa    <Ver HP inimigo>    [%d]   |\n", nomeAtaque[2], nomeAtaque[3], itemQ[2]);
				System.out.printf("|                                                                            |                                        |\n");
				System.out.printf("+----------------------------------------------------------------------------+----------------------------------------+\n");
				
				System.out.print("   > ");
				
				try {
					comando = System.console().readLine().charAt(0);
				} catch(Exception StringIndexOutOfBoundsException) {
					comando = ' ';
				}
				
				comando = Character.toUpperCase(comando);
				
				switch(comando) {
					case '0': System.out.print("   Voce nao fez nada..."); break;
					
					// Ataques
					case '1': ;
					case '2': ;
					case '3': ;
					case '4': _ataque(comando); break;
					
					// Itens
					case 'Q': ;
					case 'W': ;
					case 'E': _usarItem(comando); break;
					
					// Sair do jogo
					case 'X': quitmenu = true; 
						while(quitmenu) {
							System.out.print("   Sair do jogo? (Voce perde o progresso da partida) \n   S = Sim, N = Nao \n   > ");
							try {
								quitOp = System.console().readLine().charAt(0);
							} catch(Exception StringIndexOutOfBoundsException) {
								quitOp = ' ';
							}
							quitOp = Character.toUpperCase(quitOp);
							switch(quitOp) {
								case 'S': System.out.print("   Retornando ao menu principal...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); jogando = false; quitmenu = false; break;
								case 'N': System.out.print("   Voltando ao jogo..."); quitmenu = false; break;
								default: System.out.println("   Opcao invalida.\n"); break;
							}
						} turnoValido = false;
					break;
					
					// Sair do jogo instantaneamente
					case '/': System.out.println("   Programa finalizado com comando '/'."); System.exit(0); break;
					
					default: System.out.print("   Nenhum comando especificado."); turnoValido = false; break;
				}
				
				if(turnoValido && hpE > 0 && comando != 'Q')
					_ataqueInimigo();
				else if(comando == 'Q' && turnoValido)
					System.out.print("\n   Voce desviou do ataque inimigo!");
				else
					System.out.println("");
				
				_verificarDardo();
				
				itemQ[0] = itemQ[0] < 0 ? 0 : itemQ[0];
				itemQ[1] = itemQ[1] < 0 ? 0 : itemQ[1];
				itemQ[2] = itemQ[2] < 0 ? 0 : itemQ[2];
				
				hp = hp > 100 ? 100 : hp;
				hp = hp < 0 ? 0 : hp;
				
				hpE = hpE > 100 ? 100 : hpE;
				hpE = hpE < 0 ? 0 : hpE;
				
				hpHud = _hpNaHUD(hp);
				hpEHud = _hpNaHUD(hpE);
				
				if(turnoValido) turno++;
				
				_usarLupa(turno); lupaUsada = false;
				turnoValido = true;
				
				if(hp == 0 || hpE == 0 || turno >= 40) {
					char olho = 'o';
					if(hp == 0) olho = 'x';
					System.out.printf("\u0007\n+-----{ Turno %02d }----------------------------------------------------------------------------------------------------+\n", turno);
					System.out.printf("|                                                                                                                     |\n");
					System.out.printf("|                                                                                        %5s HP %s <%3s>    |\n", efeitoE, hpEHud, hpEMostrar);
					System.out.printf("|                                                                                                                     |\n");
					System.out.printf("|                                                                                            |\\       /|              |\n");
					System.out.printf("|                                                                                            | \\  _  / |              |\n");
					System.out.printf("|                                                                                             \\ \\/%%\\/ /               |\n");
					System.out.printf("|                                                                                              | \\v/ |                |\n");
					System.out.printf("|                 _                                                                             \\   /                 |\n");
					System.out.printf("|                /%c\\  _                                                                         /\\_/\\                 |\n", olho);
					System.out.printf("|                \\_/  +)                                                                       d     b                |\n");
					System.out.printf("|                /|\\__|                                                                                               |\n");
					System.out.printf("|               / |   |                                                                                               |\n");
					System.out.printf("|                / \\                                                                                                  |\n");
					System.out.printf("|               /   \\                                                                                                 |\n");
					System.out.printf("|                                                                                                                     |\n");
					System.out.printf("|           HP %s <%3d>                                                                                       |\n", hpHud, hp);
					System.out.printf("|                                                                                                                     |\n");
					System.out.printf("|                                                                                                                     |\n");
					System.out.printf("+-----{ Ataques }------------------------------------------------------------+-----{ Itens }--------------------------+\n");
					System.out.printf("|                   .      .              .                .                 | .               .         .            |\n");
					System.out.printf("|           .           .           .                  .           .         |         .           .            .     |\n");
					System.out.printf("|   .                          .                .           .            .   |                .             .         |\n");
					System.out.printf("|                .         .                .        .                       |   .       .            .               |\n");
					System.out.printf("|      .                          .                             .      .     |                    .                .  |\n");
					System.out.printf("+----------------------------------------------------------------------------+----------------------------------------+\n");
					
					olho = 'o';
					
					System.out.print("   Pressione 'ENTER' ");
					String enter = System.console().readLine();
					
					_fimDeJogo();
				}
			}
		} while(menu);
	}
	
	// Resetar variaveis do jogo
	static void _resetValues() {
		turno = 1;
		hp = 100;
		hpE = 100;
		itemQ[0] = 1;
		itemQ[1] = 1;
		itemQ[2] = 2;
		hpHud = "OOOOOOOOOO";
		hpEHud = "OOOOOOOOOO";
	}
	
	// Mostra os "coracoes" na HUD
	static String _hpNaHUD(int h) {
		String hud = "";
		String o0[] = {
			"OOOOOOOOOO", "OOOOOOOOOC", "OOOOOOOOO.", 
			"OOOOOOOOC.", "OOOOOOOO..", "OOOOOOOC..", 
			"OOOOOOO...", "OOOOOOC...", "OOOOOO....", 
			"OOOOOC....", "OOOOO.....", "OOOOC.....", 
			"OOOO......", "OOOC......", "OOO.......", 
			"OOC.......", "OO........", "OC........", 
			"O.........", "C.........", "..........", 
			};
			
		if(h > 0 && h <= 5) return hud = o0[19]; else if(h <= 0) return hud = o0[20]; else {
			for(int x = 100, y = 0; x >= 0 && y < o0.length; x -= 5, y++) {
				if(h >= x) {
					hud = o0[y];
					return hud;
				}
			}
		}
		
		return "|E|R|R|O||";
	}
	
	// Realizar ataque
	/*
		1 = 5 - 8, 99% de chance de acerto
		2 = 10, -2 - 0, 89% de chance de acerto
		3 = 13, -4 - 0, 73% de chance de acerto
		4 = 20, 13% de chance de acerto
	*/
	static void _ataque(char a) {
		int danoAtaque = 0, idA = Character.getNumericValue(a) - 1, danoDevolvido = 0;
		String efeitoAtaque = "";
		boolean hit = true;
		
		int chanceAt = cA.nextInt(100) + 1;
		
		switch(a) { //random.nextInt(max - min + 1) + min
			case '1': if(chanceAt <= 99) danoAtaque = sortDMG.nextInt(8 - 5 + 1) + 5; else hit = false; break;
			case '2': if(chanceAt <= 89) {danoAtaque = 10; danoDevolvido = sortDMG.nextInt(3);} else hit = false; break;
			case '3': if(chanceAt <= 73) {danoAtaque = 13; danoDevolvido = sortDMG.nextInt(5);} else hit = false; break;
			case '4': if(chanceAt <= 13) danoAtaque = 20; else hit = false; break;
		}
		
		if(hit) {
			hpE -= danoAtaque;
			hp -= danoDevolvido;
			if(danoDevolvido == 0)
				System.out.printf("   Voce usou o ataque '%s'. %d de dano causado. ", nomeAtaque[idA], danoAtaque);
			else
				System.out.printf("   Voce usou o ataque '%s'. %d de dano causado e %d de dano sofrido pelo movimento. ", nomeAtaque[idA], danoAtaque, danoDevolvido);
		} else 
			System.out.print("   Voce errou seu ataque!");
	}
	
	// Ataque do inimigo
	static void _ataqueInimigo() {
		int ataqueSorteado = ataqueE.nextInt(4), danoAtaqueE = 0, chanceAtE = cA.nextInt(100) + 1;
		boolean hitE = true;
		
		//refazer escolha de ataques do inimigo
		
		switch(ataqueSorteado) {
			case 0: if(chanceAtE <= 100) danoAtaqueE = sortDMG.nextInt(7 - 5 + 1) + 5; else hitE = false; break;
			case 1: if(chanceAtE <= 88) danoAtaqueE = sortDMG.nextInt(10 - 8 + 1) + 8; else hitE = false; break;
			case 2: if(chanceAtE <= 73) danoAtaqueE = sortDMG.nextInt(13 - 10 + 1) + 10; else hitE = false; break;
			case 3: if(chanceAtE <= 14) danoAtaqueE = sortDMG.nextInt(20 - 17 + 1) + 17; else hitE = false; break;
		}
		
		if(hitE) {
			hp -= danoAtaqueE;
			System.out.printf("\n   Inimigo usou o ataque '%s'. %d de dano sofrido.", nomeAtaqueE[ataqueSorteado], danoAtaqueE);
		} else
			System.out.print("\n   O inimigo errou o ataque!");
	}
	
	// Usar itens
	static void _usarItem(char i) {
		if(itemQ[0] == 0 && itemQ[1] == 0 && itemQ[2] == 0) {System.out.print("   Voce nao possui nenhum item!"); turnoValido = false;}
		else {
			switch(i) {
				case 'Q': if(itemQ[0] > 0) {
					System.out.print("   Voce usou o item 'Remedio'! 10 HP curados."); hp += 10; itemQ[0]--;
					} else {System.out.print("   Voce nao possui este item!"); turnoValido = false;}
				break;
				
				case 'W': if(itemQ[1] > 0) {
					System.out.print("   Voce usou o item 'Dardo'! Inimigo envenenado por 3 turnos."); _usarDardo(); itemQ[1]--;
					} else {System.out.print("   Voce nao possui este item!"); turnoValido = false;}
				break;
				
				case 'E': if(itemQ[2] > 0) {
						if(hpEMostrar != "???") {
							System.out.print("   Este item ja esta em uso! "); turnoValido = false;
						} else {
							System.out.printf("   Voce usou o item 'Lupa'! HP inimigo mostrado por um turno."); lupaUsada = true; _usarLupa(turno); itemQ[2]--;
						}
					} else {System.out.print("   Voce nao possui este item!"); turnoValido = false;}
				break;
			}
		}
	}
	
	// Item "Lupa"
	static void _usarLupa(int t) {
		if(t == turno && lupaUsada == true) {
			hpEMostrar = Integer.toString(hpE);
		}
		else if(turnoValido) {
			hpEMostrar = "???";
		}
	}
	
	// Item "Dardo", efeito "envenenado"
	static void _usarDardo() {
		envAteTurno = turno + 3;
	}
	static void _verificarDardo() {
		int danoEnv = sortDMG.nextInt(3 - 2 + 1) + 2;
		if(turno < envAteTurno) {
			efeitoE = "[ENV]";
			if(turnoValido) {
				hpE -= danoEnv;
				if(envAteTurno - turno == 1) System.out.printf(" Inimigo sofreu %d de dano pelo envenenamento (+1 turno).", danoEnv);
				else System.out.printf(" Inimigo sofreu %d de dano pelo envenenamento (+%d turnos).", danoEnv, envAteTurno - turno);
			}
		} else {
			if(turno == envAteTurno && turnoValido) System.out.print(" Inimigo nao esta mais envenenado.");
			efeitoE = "";
		}
	}
	
	// Fim de jogo (empate, vitoria ou derrota)
	static void _fimDeJogo() {
		String msg;
		char fimOp;
		boolean fimMenu = true;
		
		if(hp == 0 && hpE == 0) {
			msg = "Empate!         ";
		} else if(hp != 0) {
			msg = "Parabens! Voce venceu! :D";
		} else {
			msg = "Voce perdeu... :(    ";
		}
		
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		do {
			System.out.printf("+-------------------------------------------------+\n");
			System.out.printf("|                   Fim de Jogo                   |\n");
			System.out.printf("|                                                 |\n");
			System.out.printf("|            %25s            |\n", msg);
			System.out.printf("|                                                 |\n");
			System.out.printf("|      1. Voltar ao menu   2. Fechar jogo         |\n");
			System.out.printf("|                                                 |\n");
			System.out.printf("+-------------------------------------------------+\n");
			
			System.out.print("   > ");
			
			try {
				fimOp = System.console().readLine().charAt(0);
			} catch(Exception StringIndexOutOfBoundsException) {
				fimOp = ' ';
			}
			
			switch(fimOp) {
				case '1': System.out.print("   Retornando ao menu principal...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); fimMenu = false; break;
				case '2': System.out.println("   Programa finalizado."); System.exit(0); break;
				default: System.out.println("   Opcao invalida.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); break;
			}
		} while(fimMenu);
		
		jogando = false;
	}
}