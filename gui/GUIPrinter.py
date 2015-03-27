from PyQt4 import QtGui
from chess.pgn import *
import time

class GUIPrinter():
    def __init__(self):
        self.current = None
        self.san_html = ""
        self.sans = []
        self.offset_table = []
        self.uci = ""

    def to_uci(self,current):
        self.current = current
        rev_moves = []
        node = current
        while(node.parent):
            rev_moves.append(node.move.uci())
            node = node.parent
        moves = " ".join(reversed(rev_moves))
        fen = node.board().fen()
        fen_current = self.current.board().fen()
        print("current fen:"+fen_current)
        uci = "position fen "+fen+" moves "+moves
        return fen_current, uci


    def to_san_html(self,current):
        self.current = current
        self.offset_table = []
        self.san_html = ""
        #self.print_san(current.root(),0,False)
        #return self.san_html
        exporter = StringExporter(columns=None)
        start = time.clock()
        current.root().export_html(exporter,current,self.offset_table)

        #current.root().export(exporter,current)

        # do formatting of plain text with regexp here by
        # making all variations grey
        # [ ] must be then blocked for comments, otherwise
        # highlighting doesn't work
        # for highlighting current move, look up current move in offset table
        # then insert highlighting at offsets
        start_idx = -1
        end_idx = - 1
        game = exporter.__str__()
        for i in range(0,len(self.offset_table)):
            if(self.offset_table[i][2] == current):
                start_idx = self.offset_table[i][0]
                end_idx = self.offset_table[i][1]
        #if(current.board().turn == chess.WHITE):
        if(not start_idx == -1):
            game = game[:end_idx] + "</span>" + game[end_idx:]
            game = game[:start_idx] + '<span style="color:darkgoldenrod">' + game[start_idx:]
        #game = game[:start_idx] + '<span style="color:red">' + game[start_idx:]
        print(game)
        #else:
        #    game = game[:end_idx] + "</span>" + game[end_idx:]
        #    game = game[:start_idx+3] + '<span style="color:darkgoldenrod">' + game[start_idx+3:]


        game1 = re.sub("\[",'<dd><em><span style="color:gray">[',game)
        game2 = re.sub("]",']</dd></em></span>',game1)
        #pos = game2.index('')
        #game2 = game2[:pos-5] + '<span style="color:darkgoldenrod">' + game2[pos-5:pos] + '</span>' + game2[pos:]
        #game2 = re.sub("????",'] </dd></em></span>',game1)
        #start_idx = 0
        #end_idx = 0
        print("export to html: "+str(time.clock() - start))



        return game2

        #print("GAME: "+game)
        #test = QtGui.QTextEdit()
        #test.setHtml(game2)
        #print("HTML: "+test.toPlainText())

        #with open("html.txt", "w") as text_file:
        #    text_file.write(test.toPlainText())

        #with open("plain.txt", "w") as text_file:
        #    text_file.write(game)

        #print("are equal:"+str(test.toPlainText() == game2))
        #return game1
        #self.write_token('<dd><em><span style="color:gray">[ ')

        #return self.export_string(current.root())
        #return (exporter.__str__())