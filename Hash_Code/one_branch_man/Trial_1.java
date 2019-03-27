package net.codestrikes.adapter;
import net.codestrikes.sdk.Area;
import net.codestrikes.sdk.BotBase;
import net.codestrikes.sdk.MoveCollection;
import net.codestrikes.sdk.RoundContext;

public class PlayerBot extends BotBase {
		public MoveCollection nextMove(RoundContext context) {

          if (Math.random() > 0.5)
			context.getMyMoves()
				.addAttack(Area.HookPunch)
				.addAttack(Area.HookPunch)
				.addAttack(Area.HookKick)
				.addAttack(Area.UppercutPunch);
          else
			context.getMyMoves()
				.addAttack(Area.HookKick)
				.addAttack(Area.HookPunch)
            	.addAttack(Area.LowKick)
				.addAttack(Area.UppercutPunch)
				.addAttack(Area.UppercutPunch);

			return context.getMyMoves();
		}
}
