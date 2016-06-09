package com.wacajou.data.jpa.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wacajou.data.jpa.domain.Module;
import com.wacajou.data.jpa.domain.Comments;
import com.wacajou.data.jpa.domain.Parcours;
import com.wacajou.data.jpa.domain.Rating;
import com.wacajou.data.jpa.domain.User;
import com.wacajou.data.jpa.repository.CommentsRepository;
import com.wacajou.data.jpa.service.CommentService;

@Service
public abstract class CommentServiceImpl<T> implements CommentService<T>{

	@Autowired
	private CommentsRepository commentsRepository;

	@Override
	public List<Comments> getComments(T t) throws ServiceException {
		if(t.getClass().equals(Module.class))
			return commentsRepository.findByModule((Module) t);
		else if(t.getClass().equals(Parcours.class))
			return commentsRepository.findByParcours((Parcours) t); 
		else if(t.getClass().equals(User.class))
			return commentsRepository.findByUser((User) t);
		return null;
	}

	@Override
	public Comments getLast(T t) throws ServiceException {
		Comments last = null;
		List<Comments> comments = getComments(t);
		if(comments != null){
			last = comments.get(0);
			for(Comments comment: comments)
				if(comment.getLogdate().after(last.getLogdate()))
					last = comment;
		}
		return last;
	}

	@Override
	public List<Comments> getLastDays(T t, int days) throws ServiceException {
		Date actualDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(actualDate);
		cal.add(Calendar.DATE, -days); 
		actualDate = cal.getTime();
		
		List<Comments> last = new ArrayList<Comments>();
		List<Comments> comments = getComments(t);
		if(comments != null )
			for(Comments comment: comments)
				if(comment.getLogdate().after(actualDate))
					last.add(comment);
		return last;
	}

	@Override
	public void Evaluate(T t, User user, HashMap<String,Object> map) throws ServiceException {
		Comments comment;
		if(t.getClass().equals(Module.class))
			comment = new Comments((Module) t, user, (String) map.get("title"), (Rating) map.get("rating"), (String) map.get("message"));
		else if(t.getClass().equals(Parcours.class))
			comment = new Comments((Parcours) t, user, (String) map.get("title"), (Rating) map.get("rating"), (String) map.get("message"));
		else
			return;
		commentsRepository.save(comment);
	}

	@Override
	public float getAverage(T t, List<Comments> comments) throws ServiceException {
		float average = 0;
		for(Comments comment: comments)
			average += (float) comment.getRating().getValue();
		average = average / comments.size();
		return 0;
	}
	
	
}
