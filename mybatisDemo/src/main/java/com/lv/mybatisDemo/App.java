package com.lv.mybatisDemo;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.lv.pojo.Answer;
import com.lv.pojo.Player;
import com.lv.pojo.Question;

public class App {
	public static void main(String[] args) {

		// mybatis的配置文件
		String resource = "mybatis_conf.xml";
		// 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = App.class.getClassLoader().getResourceAsStream(resource);

		// 构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		// 创建能执行映射文件中sql的sqlSession
		SqlSession session = sessionFactory.openSession();
		
		session.commit();
		session.close();
	}

	public static void manyToOne(SqlSession session){
		String statement = "com.lv.mapping.answerMapper.getAnswerById";
		Answer answer = session.selectOne(statement, 1);
		System.out.println(answer);
		System.out.println(answer.getPlayer());
		System.out.println(answer.getQuestion());
	}
	
	public static void oneToMany(SqlSession session) {
		String statement = "com.lv.mapping.playerMapper.getPlayerById";
		Player player = session.selectOne(statement, 1);
		System.out.println(player);
		print(player.getQuestions());
		print(player.getAnswers());
	}

	public static void selectPlayer(SqlSession session) {
		/**
		 * 映射sql的标识字符串， com.lv.mapping.playerMapper是playerMapper.
		 * xml文件中mapper标签的namespace属性的值，
		 * getAllPlayers是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
		 */
		String statement = "com.lv.mapping.playerMapper.getAllPlayers";
		List<Player> players = session.selectList(statement);
		print(players);

		String statement2 = "com.lv.mapping.playerMapper.getPlayerById";
		Player player = session.selectOne(statement2, 1);
		System.out.println("byid: " + player);

		String statement3 = "com.lv.mapping.questionMapper.getAllQuestions";
		List<Question> questions = session.selectList(statement3);
		print(questions);

		String statement4 = "com.lv.mapping.questionMapper.getQuestionById";
		Question question = session.selectOne(statement4, 1);
		System.out.println(question);
	}

	public static void addPlayer(SqlSession session, Player player) {
		String statement = "com.lv.mapping.playerMapper.insertPlayer";
		System.out.println("before: " + player);
		int count = session.insert(statement, player);
		System.out.println("count: " + count);
		System.out.println("after: " + player);
	}

	public static void updatePlayer(SqlSession session, Player player) {
		String statement = "com.lv.mapping.playerMapper.updatePlayer";
		int count = session.update(statement, player);
		System.out.println("count: " + count);
		System.out.println(player);
	}

	public static void deletePlayer(SqlSession session, Player player) {
		String statement = "com.lv.mapping.playerMapper.deletePlayer";
		int count = session.delete(statement, player);
		System.out.println("count: " + count);
	}

	public static void print(List<?> list) {
		for (Object object : list) {
			System.out.println(object);
		}
	}
}
