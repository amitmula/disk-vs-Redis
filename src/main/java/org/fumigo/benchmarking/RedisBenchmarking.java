package org.fumigo.benchmarking;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

public class RedisBenchmarking {
	static Logger _logger = LoggerFactory.getLogger("metrics");
	static MetricRegistry _metrics = new MetricRegistry();
	static Counter _logCounts = _metrics.counter(MetricRegistry.name(RedisBenchmarking.class, "counter"));
	static Meter _logMeter = _metrics.meter(MetricRegistry.name(RedisBenchmarking.class, "meter"));
	static Timer _logTimer = _metrics.timer(MetricRegistry.name(RedisBenchmarking.class, "responses"));
	static Graphite graphite = new Graphite(new InetSocketAddress("0.0.0.0", 2003));
	static GraphiteReporter _reporter = GraphiteReporter.forRegistry(_metrics)
      .prefixedWith("redis")
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .filter(MetricFilter.ALL)
      .build(graphite);
	
	public static void main(String args[]) throws InterruptedException {
		_reporter.start(10, TimeUnit.SECONDS);
		for(int i=0;;) {
			logit("sadtadtydtaysdtuysdtausydtsyudtsydtaudtystduasydtasyudtasyudtasyudtsayudtsudtausydtauysdtusdtuasydtyusdtuystdusaydtuydtsauydtsudtsuadtsuydtaasydtasyudtsuydtyudtasyudtsuydtasuytd");
		}
	}
	
	static void logit(String str) {
		final Timer.Context context = _logTimer.time();
		try {
			_logger.info(str);
		}finally {
			context.stop();
		}
		_logMeter.mark();
		_logCounts.inc();
	}
}
