package main.org;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Chat {

	static NioSocketAcceptor socketAcceptor;

	public static void main(String[] args) {

		int processorCount = Runtime.getRuntime().availableProcessors();
		socketAcceptor = new NioSocketAcceptor(processorCount);
		socketAcceptor.setReuseAddress(true);
		socketAcceptor.getSessionConfig().setReadBufferSize( 2048 );
		socketAcceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 1 );
		socketAcceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.DEFAULT, new LineDelimiter("$"))));
		socketAcceptor.setHandler(new Handler());
		try {
			socketAcceptor.bind(new InetSocketAddress(5222));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
