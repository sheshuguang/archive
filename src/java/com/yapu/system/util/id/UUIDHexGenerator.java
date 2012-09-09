package com.yapu.system.util.id;

import java.io.Serializable;

public class UUIDHexGenerator extends AbstractUUIDGenerator{

        private String sep = "";

        protected String format(int intval) {
                String formatted = Integer.toHexString(intval);
                StringBuffer buf = new StringBuffer("00000000");
                buf.replace( 8-formatted.length(), 8, formatted );
                return buf.toString();
        }

        protected String format(short shortval) {
                String formatted = Integer.toHexString(shortval);
                StringBuffer buf = new StringBuffer("0000");
                buf.replace( 4-formatted.length(), 4, formatted );
                return buf.toString();
        }

        public Serializable generate() {
                return new StringBuffer(36)
                        .append( format( getIP() ) ).append(sep)
                        .append( format( getJVM() ) ).append(sep)
                        .append( format( getHiTime() ) ).append(sep)
                        .append( format( getLoTime() ) ).append(sep)
                        .append( format( getCount() ) )
                        .toString();
        }
//        public static void main( String[] args ) throws Exception {
//            UUIDHexGenerator g = new UUIDHexGenerator();
//            for(int i=0; i<10; i++)
//            {
//                String id = (String)g.generate();
//                System.out.println("=======id="+id);
//            }
//        }

}
