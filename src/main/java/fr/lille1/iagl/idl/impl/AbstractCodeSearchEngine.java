package fr.lille1.iagl.idl.impl;

import java.util.List;

import fr.lille1.iagl.idl.CodeSearchEngine;

public abstract class AbstractCodeSearchEngine implements CodeSearchEngine {

	public class LocationImpl implements Location {

		@Override
		public String getFilePath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLineNumber() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public class Typeimpl implements Type {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getFullyQualifiedPackageName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TypeKind getKind() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Location getDeclaration() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public class MemberImpl implements Member {

		@Override
		public Type getDeclaringType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public class Fieldimpl implements Field {

		@Override
		public Type getDeclaringType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public class MethodImpl implements Method {

		@Override
		public Type getDeclaringType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Type> getParamaters() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
