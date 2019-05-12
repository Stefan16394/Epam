import com.epam.belote.bonus.Quad;
import com.epam.belote.cards.CardType;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class QuadBonusesTests {
    @Test
    public void fourJacksShouldReturn200Points(){
        Quad quad = new Quad(CardType.JACK);
        Assertions.assertEquals(200, quad.getBonus());
    }

    @Test
    public void fourNinesShouldReturn150Points(){
        Quad quad = new Quad(CardType.NINE);
        Assertions.assertEquals(150,quad.getBonus());
    }

    @Test
    public void fourTensKingsQueensAcesShouldReturn100Points(){
        Quad quadOfAces = new Quad(CardType.ACE);
        Quad quadOfKings = new Quad(CardType.KING);
        Quad quadOfQueens = new Quad(CardType.QUEEN);
        Quad quadOfTens = new Quad(CardType.TEN);
        Assertions.assertAll(()->{
            Assertions.assertEquals(100,quadOfAces.getBonus());
            Assertions.assertEquals(100,quadOfKings.getBonus());
            Assertions.assertEquals(100,quadOfQueens.getBonus());
            Assertions.assertEquals(100,quadOfTens.getBonus());
        });
    }
}
