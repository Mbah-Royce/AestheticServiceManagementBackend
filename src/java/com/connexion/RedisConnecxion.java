/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connexion;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Mbah Royce
 */
public class RedisConnecxion {
//    production
    private final static String host = "redis-13510.c3.eu-west-1-1.ec2.cloud.redislabs.com";
    private final static String password = "i0GHBsg2ETJajmXewv9vce3XEBmvBDvQ";
    private final static int port = 13510;
    
    //development
//    private final static String host = "localhost";
//    private final static String password = "null";
//    private final static int port = 6379;
    
    public static Jedis redisConnect(){
        Jedis jedis = new Jedis(host,port);
        jedis.auth(password);
        return jedis;
    }
    
}