import java.util.List;

public class Mind {
    public final int MIND_SIZE = 64;
    int cur; //Текущая команда для выполнения, указатель
    List<Integer> genom;

    public Mind(List<Integer> genom) {
        this.cur = 0;
        this.genom = genom;
    }

    public void mutate() {
        int rnd = (int) (Math.random()*genom.size());
        genom.set(rnd, (int) (Math.random()*MIND_SIZE));
    }

    public int getParam() {
        int param = cur + 1;
        if (param >= genom.size()) {
            param -= genom.size();
        }
        return genom.get(param);
    }

    public void incCur(int num) {
        cur += num;
        if (cur >= genom.size())
            cur -= genom.size();
    }

    public void indirectIncCur(int num) {
        int q = cur + num;
        if (q >= genom.size())
            q -= genom.size();
        int cmd = genom.get(q);
        incCur(cmd);
    }
}
