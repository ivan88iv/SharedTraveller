package org.shared.traveller.business.authentication.domain;

/**
 * This class is used for storing authenticated user details. It is created and
 * initialized from authentication framework for each protected service request.
 */
// TODO Extend this class with all needed data
public class AuthenticatedUser
{
	private Long id;

	private String username;

	public AuthenticatedUser(Long id, String username)
	{
		super();
		this.id = id;
		this.username = username;
	}

	/**
	 * @return authenticated user database id.
	 */
	public Long getId()
	{
		return id;

	}

	/**
	 * @return authenticated user username
	 */
	public String getUsername()
	{
		return username;
	}
}
