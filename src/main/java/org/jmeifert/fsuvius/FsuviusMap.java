package org.jmeifert.fsuvius;

/**
 * FsuviusMap contains user-modifiable constants that are used elsewhere in the program.
 */
public class FsuviusMap {
    /**
     * The maximum amount of requests per second the server will handle
     * before refusing additional requests.
     */
    public static final int MAX_REQUESTS_PER_SECOND = 50;

    /**
     * The default photo for new users as base64.
     */
    public static final String DEFAULT_PHOTO = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAC4jAAAuIwF4pT92AAAE1ElEQVR42u2aSSxzXRjH/17S1xxi2hQxJKKhK7ERmyKIjQS7mrc2iAgLiaRiQcSiIcFGKjEtiBJi2phJDA2q2mKB1jxEWzqdb9d8l1YRXrTnn9zFc+5z7rnnl3Oe+5yndSGEEDix/sDJRQFQABQABUABUAAUAAVAAVAAFIBzyu21m2azGTKZDKurq1hfX4dMJsPGxgbOz88tPru7u4iNjf29BIgNXV1dkaqqKgLg1Wt3d5f8ZlkFoNPpSGlpqd3JOyyAqakpxiRLS0uJVColWq2WmM1m4kiyCqCsrIwBQKVSEUfVCwCPj4+Myefn5xNH1ovPoEajYdhRUVHOlQfo9Xrmd9LNzXnzgI9Kr9dje3sbEokEW1tbkMvlkMvlCAoKQnh4OEJCQuDv74/Q0FCEh4cjMjISbDYbrq6u35MHsNnsN33ynl8KhYKxn8xmM5mfnyfJycnvftbs7KzNfXpwcMDwbW9vf9c+fx7X+Hy+5Z7bJ4JEd3c3iouLnfMssLi4yJg8m82GSCTC/v4+7u7uYDAYYDKZoNVqoVarsba2ho6ODnC53J+VCqtUKsZyEQgEdpeYyWQiWVlZlj6RkZHk4ODgzctzenqaLC8v/94tcHx8jLGxMYstEAgQERHxpr5///5FSkrK794C19fXDDs6Otq5YoDBYGDYOp3OuQD4+Pgw7MHBwRcJlUMDCAsLQ3BwsMUWCoWorq7G3t4ezGaz4wPw9PREc3Mzo621tRWxsbHIzs5GT08Ptra2cHt767ipcF5eHjY3N9HS0sJoF4vFEIvFFpvP5yM1NRUJCQmIiYn59rPGpyVC7u7uaGxsxMDAANhstk2/np4eFBUVIS4uDunp6ZicnHwRRH9tVZjFYiEvLw87OzuYmZlBXV3dqzBmZ2eRnp6OiooK3N/fO05Z3NfXFzweD/X19VAqlVAoFBgfH0dDQ4NVIEKhEAKBAF/1d6XXAvGX/y7AYrEQFRWFjIwM1NbWQi6XY2VlBYWFhQy/pqYm7OzsWH/JP8zXNJlM73qHx8fHf1sPsBcrEhMTweVy4eXlhba2Nsu9zc1NxMXFWe3zf713uzzPVP/pCngNREFBAaNNpVJZ9fXw8GDYEonkXWPJZLKfBwAA/Pz8XhyMbMWUzMxMi93X14ebm5s3jWE0GtHV1fUzAZycnDDs0NBQm75ZWVkMe2Fh4U1jjIyMYGho6GsBjI+PQywW4+rq6s19zs7O0NDQwGhLSEiw6c/j8Rh2ZWUljo6OXq1QTUxMICcn5+sLIr29vRb/srIyMjw8TCQSCTk/PydarZYYjUZiNBrJw8MDUSqVZGBggHC5XMY4nZ2ddosuNTU1jD4cDoeMjo6Ss7MzotfricFgINfX12RtbY1UVFRY/Pr7+20WRD4dwEeu5uZm8vT0ZHeci4sLkpmZ+a5ni0QiotPpbAL41hiQlpaGubk5lJeXg8Vi2fUPDAyESCRCZWWlXd+kpCTMz8+Dz+fDxcXla/OA3Nxc8Hg8qNVqnJyc4PDwEGq1GpeXlzg9PYVUKoW3tzciIiLA4XDA4XAQHx//ocNQQEAAmpqaUFJSgtXVVSwtLUEqlUKj0VgOWMnJyYiPj3+RP1iTC/27vJOLAqAAKAAKgAKgACgACoACoAAoAAqAAqAAnFD/AZZ5oHgkh6rAAAAAAElFTkSuQmCC";

    /**
     * The maximum allowed size for photos. 1MB recommended.
     */
    public static final int MAX_PHOTO_SIZE = 1024 * 1024;
}
