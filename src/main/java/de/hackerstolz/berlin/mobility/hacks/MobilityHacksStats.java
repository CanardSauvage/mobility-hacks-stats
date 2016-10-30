package de.hackerstolz.berlin.mobility.hacks;

public class MobilityHacksStats {
    public Long totalSoldTickets = 0L;

    public Long totalSoldTicketsDeveloper = 0L;
    public Long maxTicketsDeveloper = 70L;

    public Long totalSoldTicketsDesigner = 0L;
    public Long maxTicketsDesigner = 20L;

    public Long totalSoldTicketsAstronaut = 0L;
    public Long maxTicketsAstronaut = 10L;

    public Long daysUntilHackathon = (1480633200000L - System.currentTimeMillis()) / 1000 / 60 / 60 / 24;

//    public static void main(String[] args) throws Exception {
//        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-02");
//        // FIXME Remove System.out
//        System.out.println(parse.getTime());
//// FIXME Remove System.out
//        System.out.println("Current: " + System.currentTimeMillis());
//        // FIXME Remove System.out
//        System.out.println(1480633200000L - System.currentTimeMillis());
//        // FIXME Remove System.out
//        System.out.println((1480633200000L - System.currentTimeMillis()) / 1000 / 60 / 60 / 24);
//    }
}
